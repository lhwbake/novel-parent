package org.novel.common.utils;

import java.util.ArrayList;
import java.util.List;

public class ArrayUtils {

	/**
	 * 将一个数组按照固定大小进行拆分成数组
	 * 
	 * @param ary
	 * @param subSize
	 * @return
	 */
	public static Object[] splitAry(Object[] ary, int subSize) {
		int count = ary.length % subSize == 0 ? ary.length / subSize : ary.length / subSize + 1;

		List<List<Object>> subAryList = new ArrayList<List<Object>>();

		for (int i = 0; i < count; i++) {
			int index = i * subSize;
			List<Object> list = new ArrayList<Object>();
			int j = 0;
			while (j < subSize && index < ary.length) {
				list.add(ary[index++]);
				j++;
			}
			subAryList.add(list);
		}

		//将list转化为数组		
		Object[] subAry = new Object[subAryList.size()];

		for (int i = 0; i < subAryList.size(); i++) {
			List<Object> subList = subAryList.get(i);
			Object[] subAryItem = new Object[subList.size()];
			for (int j = 0; j < subList.size(); j++) {
				subAryItem[j] = subList.get(j);
			}
			subAry[i] = subAryItem;
		}

		return subAry;
	}
}
