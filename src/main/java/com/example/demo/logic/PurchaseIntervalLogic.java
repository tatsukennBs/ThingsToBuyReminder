package com.example.demo.logic;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 購入間隔に関する業務ロジック定義クラス
 *
 */
public class PurchaseIntervalLogic {

	/**
	 *  購入間隔の平均値を算出するメソッド
	 * @param list IDに紐付く最終購入日のDate型List
	 * @return 平均購入間隔
	 */
	public int calcaveragePurchaseInterval(List<Date> list) {

		//最終購入日のListを昇順に並べ替え
		Collections.sort(list);
		
		long[] intervalarray = new long[list.size() - 1];
		
		//各レコードの購入間隔を算出し、配列に格納する
		for (int i = 0 ; i  <  list.size()  - 1 ; i++) {
			
			Duration interval = Duration.between(list.get(i).toInstant(), list.get(i + 1).toInstant());
			intervalarray[i] = interval.toDays();
		}
		//配列に格納した購入間隔の平均値を算出する
		double interval = Arrays.stream(intervalarray).average().getAsDouble();
		
		return (int)interval;
	}
}
