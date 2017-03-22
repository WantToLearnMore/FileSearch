package com.search.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ASUS on 2017/3/9.
 */
public class ResultUtil {

    public static Map<String,String> result(String msg,int statue,String re){
        Map<String,String> map =new HashMap<>();
        map.put("msg",msg);
        map.put("statue",String.valueOf(statue));
        map.put("result",re);
        return  map;
    }




    public static Map<String,String> builtResult(Object object){
        return getValueMap(object);
    }

    //将map 转换成对应的对象
    public static Object builtObject(Map<String,String> map,Object object){

      return  setValueFromMap(map,object);
    }

   /**
    * 将map转换成对应的对象
    * @param map  需要转换的map
    * @param object   目标对象
    * */
    private  static Object setValueFromMap(Map<String,String>map,Object object){

        if(!map.isEmpty()){
            Field[] fields =getFileName(object);
            String firstLetter="";
            String fileName="";
            String setter="";

            for(String k:map.keySet()){
                for(Field field:fields){
                    fileName=field.getName();
                    firstLetter=fileName.substring(0,1);
                    setter="set"+firstLetter.toUpperCase()+fileName.substring(1);
                    if(k.equals(fileName)){
                        try {
                            if(String.valueOf(field.getType()).equals("int")){
                                Method method=object.getClass().getMethod(setter,int.class);
                                method.invoke(object,Integer.valueOf(map.get(k)));
                            }else if(String.valueOf(field.getType()).equals(Date.class)){
                                Method method=object.getClass().getMethod(setter,Date.class);
                                method.invoke(object,new Date(System.currentTimeMillis()));
                            }else {
                                Method method=object.getClass().getMethod(setter,String.class);
                                method.invoke(object,map.get(k));
                            }

                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }



        return  object;
    }



   /**
    * 根据属性集合取得对应的值并封装成map
    * 注意这里没有考虑属性类型为boole类型时的情况
    * */
    private  static Map<String,String> getValueMap(Object object){

        Field [] fields=getFileName(object);
        Map<String,String> map =new HashMap<>();

        for(Field field:fields){
            field.setAccessible(true);
            try {
                map.put(field.getName(),String.valueOf(field.get(object)));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    //取得对象的属性集合
    private  static  Field[] getFileName(Object object){
        Field[] result=object.getClass().getDeclaredFields();
        return  result;
    }

    public  static void main(String args[]){


}
}
