package com.example.demo.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Item;
import com.example.demo.service.ThingsToBuyService;

@Controller
@RequestMapping("/ThingsToBuy")
public class ThingsToBuyController {
	
	@Autowired
	private final ThingsToBuyService service;
	
	public ThingsToBuyController(ThingsToBuyService service) {
		this.service = service;
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
		
		return "regist";
	}
	
	/**
	 * 品目登録完了画面を表示します
	 * @param form
	 * @param result
	 * @param model
	 * @return resources/templates下のHTMLファイル名
	 */
	@PostMapping("/regist/complete")
	public String registComplete(
			@Validated ThingsToBuyForm form,
			BindingResult result,
			Model model) {
		
		Item item = new Item();
		item.setItemName(form.getItemName());
		item.setCategory(form.getCategory());
		item.setPurchaseDate(form.getPurchaseDate());
		
		//エラーがあった場合、自画面遷移
		if (result.hasErrors()) {
			model.addAttribute("ThingsToBuyForm", form);
			return "regist";
			
		} else {
			service.insertItem(item);
			model.addAttribute("ThingsToBuyForm", form);
			return "registcomplete";
		}		
	}
	
	/**
	 * 品目編集画面を表示します
	 * @param model
	 * @param itemid
	 * @return resources/templates下のHTMLファイル名
	 */
	@GetMapping("/edit/{id}")
	public String edit(Model model, @PathVariable("id") int itemid) {
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
	@PostMapping("/edit/{id}/complete")
	public String editComplete(
			@Validated ThingsToBuyForm form,
			BindingResult result,
			Model model,
			@PathVariable("id") int itemid) {
		
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
	@PostMapping("/delete/{id}/complete")
	public String delete(
			Model model,
			@PathVariable("id") int itemid)  {
		
		service.deleteById(itemid);
		return "deletecomplete";
	}
	
	@PostMapping("/registPurchaseDate/{id}")
	public String registPurchaseDate(
			@Validated ThingsToBuyForm form,
			BindingResult result,
			Model model,
			@PathVariable("id") int itemid) {
		
		Item item = new Item();
		item.setItemId(itemid);
		item.setPurchaseDate(form.getPurchaseDate());
		
		//エラーがあった場合、自画面遷移
		if (result.hasErrors()) {
			model.addAttribute("ThingsToBuyForm", form);
			return "index";
			
		} else {
			service.updatePurchaseDate(item);
			model.addAttribute("ThingsToBuyForm", form);
			return "index";
		}		
	}
}
