package com.example.demo.app;

import java.util.ArrayList;
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

import com.example.demo.entity.Item;
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
			service.insertItemSeq(item.getItemId());
			service.insertPurchaseInterval(item.getItemId());
			model.addAttribute("complete", "品目登録が完了しました");
			
			return "registcomplete";
		}
	}
	
	/**
	 * 品目編集画面を表示します
	 * @param model
	 * @param itemid
	 * @return resources/templates下のHTMLファイル名
	 */
	@GetMapping("/edit")
	public String edit(Model model, @RequestParam("itemid") int itemid) {
		return "edit";
	}
	
	/**
	 * 品目編集完了画面を表示します
	 * @param form
	 * @param result
	 * @param model
	 * @param itemid
	 * @return resources/templates下のHTMLファイル名
	 */
	@PostMapping("/edit/complete")
	public String editComplete(
			@Validated ThingsToBuyForm form,
			BindingResult result,
			Model model,
			@RequestParam("itemid") int itemid) {
		
		Item item = new Item();
		item.setItemId(itemid);
		item.setItemName(form.getItemName());
		item.setCategory(form.getCategory());
		item.setPurchaseDate(form.getPurchaseDate());
		
		//エラーがあった場合、自画面遷移
		if (result.hasErrors()) {
			model.addAttribute("ThingsToBuyForm", form);
			return "edit";
			
		} else {
			service.updateItem(item);
			service.updatePurchaseDate(item);
			
			//PurchaseInterval purchaseinterval = new PurchaseInterval();
			//service.insertPurchaseInterval(purchaseinterval, item.getItemId());
			model.addAttribute("ThingsToBuyForm", form);
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
	public String delete(Model model, @RequestParam("itemid") int itemid)  {
		
		//TODO 画面遷移のため一旦コメントアウト
		//service.deleteById(itemid);
		return "deletecomplete";
	}
	
	@GetMapping("/registPurchaseDate")
	public String registPurchaseDate(
			//@Validated ThingsToBuyForm form,
			//BindingResult result,
			Model model,
			@RequestParam("itemid") int itemid) {
		
		Item item = new Item();
		item.setItemId(itemid);
		//item.setPurchaseDate(form.getPurchaseDate());
		
		//エラーがあった場合、自画面遷移
		//if (result.hasErrors()) {
			//model.addAttribute("ThingsToBuyForm", form);
			//return "index";
			
		//} else {
			//TODO 画面遷移のため一旦コメントアウト、itemidを渡して
			//service.updatePurchaseDate(item);
			//model.addAttribute("ThingsToBuyForm", form);
			return "redirect:/ThingsToBuy";
		//}		
	}
}
