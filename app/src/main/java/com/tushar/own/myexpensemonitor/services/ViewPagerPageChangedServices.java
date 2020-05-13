package com.tushar.own.myexpensemonitor.services;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerPageChangedServices {

    private List<ViewPagerPageChangeListener> viewPagerPageChangeListeners = new ArrayList<>();

    private static ViewPagerPageChangedServices viewPagerPageChangedServices = new ViewPagerPageChangedServices();

    public static ViewPagerPageChangedServices getInstance() {
        if(viewPagerPageChangedServices ==null){
            Class clazz = ViewPagerPageChangedServices.class;
            synchronized (clazz){
                viewPagerPageChangedServices = new ViewPagerPageChangedServices();
            }
        }

        return viewPagerPageChangedServices;
    }

    public interface ViewPagerPageChangeListener{
        void onPageViewPagerChanged();
    }

    public void AddViewPagerPageChangedEventDoneListener(ViewPagerPageChangeListener listener){
        this.viewPagerPageChangeListeners.add(listener);
    }

    public void RemoveViewPagerPageChangedEventDoneListener(ViewPagerPageChangeListener listener){
        this.viewPagerPageChangeListeners.remove(listener);
    }

    private void updateViewOnViewPagerPageChangedEventDone() {
        for (int i = 0; i < this.viewPagerPageChangeListeners.size(); i++){
            this.viewPagerPageChangeListeners.get(i).onPageViewPagerChanged();
        }
    }

    public void changeViewPagerPageElement(){
        updateViewOnViewPagerPageChangedEventDone();
    }
}
