package com.example.demo.app;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Item;
import com.example.demo.entity.PurchaseInterval;
import com.example.demo.service.ThingsToBuyService;

@Controller
@RequestMapping("/ThingsToBuy")
public class ThingsToBuyController {
	
	private final ThingsToBuyService service;
	
	public ThingsToBuyController(ThingsToBuyService thingsToBuyService) {
		this.service = thingsToBuyService;
	}
	/**
	 * 買い物リストの一覧を表示します
	 * @param model
	 * @return  resources/templates下のHTMLファイル名
	 */
	@GetMapping
	public String index(Model model) {
		
		List<Item> itemlist = service.findLatestAll();
		model.addAttribute("itemlist", itemlist);
		
		return "index";	
	}
	
	/**
	 * 品目登録画面を表示します
	 * @param model
	 * @return resources/templates下のHTMLファイル名
	 */
	@GetMapping("/regist")
	public String regist(Model model) {
		
		model.addAttribute("thingsToBuyForm", new ThingsToBuyForm());
		
		return "regist";
	}
	
	/**
	 * 品目登録完了画面を表示します
	 * @param thingsToBuyForm
	 * @param result
	 * @param model
	 * @return resources/templates下のHTMLファイル名
	 */
	@PostMapping("/regist/complete")
	public String registComplete(
			@Validated @ModelAttribute ThingsToBuyForm thingsToBuyForm,
			BindingResult result,
			Model model) {
		
		Item item = new Item();
		
		item.setItemName(thingsToBuyForm.getItemName());
		item.setCategory(thingsToBuyForm.getCategory());
		item.setPurchaseDate(thingsToBuyForm.getPurchaseDate());
		
		//エラーがあった場合、エラーメッセージをList設定し自画面遷移
		if (result.hasErrors()) {
			
			List <String> errorList = new ArrayList<String>();
			for(ObjectError e : result.getAllErrors()) {
				
				errorList.add(e.getDefaultMessage());
			}
			model.addAttribute("ErrorList", errorList);
			
			return "regist";

		} else {
			service.insertItem(item);	
			service.insertItemSeq(item.getItemId(), item.getItemSequence());
			service.insertPurchaseInterval(item.getItemId());
			model.addAttribute("complete", "品目登録が完了しました");
			
			return "registcomplete";
		}
	}
	
	/**
	 * リスト編集画面を表示します
	 * @param form
	 * @param model
	 * @param itemid
	 * @return resources/templates下のHTMLファイル名
	 */
	@GetMapping("/edit")
	public String edit(
			@ModelAttribute ThingsToBuyForm form,
			Model model,
			@RequestParam("itemid") int itemid) {
		
		//リスト編集画面表示用に選択したデータをエンティティに格納
		Item item = service.findById(itemid);
		model.addAttribute("item", item);

		form.setItemName(item.getItemName());
		form.setCategory(item.getCategory());
		form.setPurchaseDate(item.getPurchaseDate());
		form.setPurchaseInterval(item.getPurchaseInterval());
		
		model.addAttribute("thingsToBuyForm", form);
		
		return "edit";
	}
	
	/**
	 * リスト編集完了画面を表示します
	 * @param form
	 * @param result
	 * @param model
	 * @param itemid
	 * @return resources/templates下のHTMLファイル名
	 */
	@PostMapping("/edit/complete")
	public String editComplete(
			@Validated @ModelAttribute ThingsToBuyForm form,
			BindingResult result,
			Model model,
			@RequestParam("itemid") int itemid) {
		
		Item item = new Item();
		item.setItemId(itemid);
		item.setItemName(form.getItemName());
		item.setCategory(form.getCategory());
		item.setPurchaseDate(form.getPurchaseDate());
		
		//エラーがあった場合、エラーメッセージをList設定し自画面遷移
		if (result.hasErrors()) {
			
			List <String> errorList = new ArrayList<String>();
			for(ObjectError e : result.getAllErrors()) {

				errorList.add(e.getDefaultMessage());
			}
			model.addAttribute("ErrorList", errorList);
			model.addAttribute("item", item);
			
			return "edit";
			
		} else {
			
			service.updateItem(item);
			service.updatePurchaseDate(item);
			
			//購入間隔が０（品目登録のみ、購入回数が１回）以外の場合、購入間隔を再計算する。
			if (form.getPurchaseInterval() != 0) {
				
				PurchaseInterval purchaseinterval = new PurchaseInterval();
				service.updatePurchaseInterval(purchaseinterval, itemid);
				
			}
			model.addAttribute("complete", "リスト内容の更新完了しました");
			return "editcomplete";
		}	
	}
	/**
	 * リスト削除完了画面を表示します
	 * @param model
	 * @param itemid
	 * @return resources/templates下のHTMLファイル名
	 */
	@GetMapping("/delete/complete")
	public String delete(
			Model model,
			@RequestParam("itemid") int itemid)  {
		
		//削除完了画面表示用に選択したデータをエンティティに格納
		Item item = service.findById(itemid);
		model.addAttribute("item", item);
		
		service.deleteById(itemid);
		service.deleteItemSeq(itemid);
		service.deletePurchaseInterval(itemid);
		model.addAttribute("deletecomplete", "下記項目をリストから削除しました");
		return "deletecomplete";
	}
	
	/**
	 * 購入ボタン押下時に自画面遷移を実施
	 * @param form
	 * @param model
	 * @param itemid
	 * @param redirectAttributes
	 * @return resources/templates下のHTMLファイル名
	 */
	@GetMapping("/registPurchaseDate")
	public String registPurchaseDate(
			@ModelAttribute ThingsToBuyForm form,
			Model model,
			@RequestParam("itemid") int itemid,
			RedirectAttributes redirectAttributes) {
		
		//選択したボタンに紐づくデータをエンティティに格納
		Item item = service.findById(itemid);
		
		
		//最終購入日がnull以外の場合に購入年月日同一チェックを実施
		if (item.getPurchaseDate() != null && purchaseDateCheck(item.getPurchaseDate())) {
			
			redirectAttributes.addFlashAttribute("complete", "本日日付で既に登録されています");
		} else {
			
			//最終購入日がnullの場合、itemsテーブルの最終更新日のみ更新
			if(item.getPurchaseDate() == null) {
				
				item.setPurchaseDate(new Date());
				service.updatePurchaseDate(item);
			} else {
				
				item.setPurchaseDate(new Date());
				service.insertPurchaseDate(item);
				service.insertItemSeq(itemid, item.getItemSequence());
				PurchaseInterval purchaseinterval = new PurchaseInterval();
				service.updatePurchaseInterval(purchaseinterval, itemid);
			}
			redirectAttributes.addFlashAttribute("complete", "最終購入日を更新しました");
		}
		
		return "redirect:/ThingsToBuy";
	}
	
	/**
	 *購入年月日同一チェック
	 *購入ボタンを押下した際の最終購入日と購入ボタンを押下した日が同一年月日がどうかをチェック
	 * @param purchasedate エンティティから取得した最終購入日
	 * @return true:同一年月日である／false:同一年月日ではない
	 */
	private boolean purchaseDateCheck(Date purchasedate) {
			
			SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd");
			
			//登録されている最終購入日をyyyyMMdd形式のStirng型に変換
			Calendar purchaseDateLatestCal = Calendar.getInstance();
			purchaseDateLatestCal.setTime(purchasedate);
			String purchaseDateLatest = sdf.format(purchaseDateLatestCal.getTime());
			
			//購入ボタンを押下した日をyyyyMMdd形式のStirng型に変換
			Calendar nowDateCal = Calendar.getInstance();
			String nowDate = sdf.format(nowDateCal.getTime());
			
			//最終購入日と購入ボタンを押下した日が同一年月日かどうかを返却
			return purchaseDateLatest.equals(nowDate);
		}
	
}
