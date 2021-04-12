package com.zyz.study.alibaba;

import jdk.nashorn.internal.objects.ArrayBufferView;
import sun.jvmstat.perfdata.monitor.CountedTimerTaskUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:yunzhuang.zyz@alibaba.com">朱云壮</a>
 * @version 1.0
 * @date 2021/3/4 12:43
 */
public class WordServer {
    Map<String, Integer> words = new HashMap<>();

    public void put(List<String> strings) {
        if (strings == null || strings.size() <= 0) {
            return;
        }
        strings.forEach(s -> {
            Integer count = words.get(s);
            if (count == null || count == 0) {
                words.put(s, 1);
            } else {
                words.put(s, ++count);
            }
        });
    }

    public List<String> getTop100() {
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(words.entrySet());
        entries.sort(Comparator.comparingInt(Map.Entry::getValue));
        return entries.subList(0, 100).stream().map(Map.Entry::getKey).collect(Collectors.toList());
    }

}
