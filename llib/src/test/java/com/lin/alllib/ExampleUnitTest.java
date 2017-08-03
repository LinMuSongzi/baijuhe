package com.lin.alllib;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Properties;
import java.util.Stack;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {

//        FileOutputStream fileOutputStream = new FileOutputStream("text.txt");
//        Properties properties = new Properties();
//        properties.put("name","刘德华");
//        properties.put("age","19");
//        properties.put("address","广东广州");
//        properties.put("sex","男");
//        properties.store(fileOutputStream,"user");
//        FileInputStream fileOutputStream = new FileInputStream("text.txt");
//        InputStreamReader inputStreamReader = new InputStreamReader(fileOutputStream,"UTF-8");
//        Properties properties = new Properties();
//        properties.load(inputStreamReader);
//        System.err.println(properties.get("name"));
//        System.err.println(properties.get("address"));

        Collection<Bean> stack = new Stack<>();
        stack.add(Bean.newBean("吴军"));
        stack.add(Bean.newBean("李策一"));
        stack.add(Bean.newBean("欧亚安"));
        stack.add(Bean.newBean("陈一涵"));
        stack.add(Bean.newBean("欧阳靖"));
        Observable<Bean> beanObservable = Observable.from(stack).filter(new Func1<Bean, Boolean>() {
            @Override
            public Boolean call(Bean bean) {
                if(bean.name.contains("一")) {
                    return true;
                }else{
                    return false;
                }
            }
        });
        beanObservable.subscribe(new Action1<Bean>() {
            @Override
            public void call(Bean bean) {
                System.err.println(bean.name);
            }
        });

    }

    private static class Bean{

        private String name;

        public Bean(String name) {
            this.name = name;
        }



        static Bean newBean(String name){

            return new Bean(name);

        }
    }

}