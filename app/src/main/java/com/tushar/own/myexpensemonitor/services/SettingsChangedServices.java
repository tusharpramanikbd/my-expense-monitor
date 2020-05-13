package com.tushar.own.myexpensemonitor.services;

import com.tushar.own.myexpensemonitor.listeners.SettingsChangedListener;

import java.util.ArrayList;
import java.util.List;

public class SettingsChangedServices {

    private List<SettingsChangedListener> settingsChangedListeners = new ArrayList<>();

    private static SettingsChangedServices settingsChangedServices = new SettingsChangedServices();

    public static SettingsChangedServices getInstance() {
        if(settingsChangedServices ==null){
            Class clazz = SettingsChangedServices.class;
            synchronized (clazz){
                settingsChangedServices = new SettingsChangedServices();
            }
        }

        return settingsChangedServices;
    }

    public void AddSettingsChangedEventDoneListener(SettingsChangedListener listener){
        this.settingsChangedListeners.add(listener);
    }

    public void RemoveSettingsChangedEventDoneListener(SettingsChangedListener listener){
        this.settingsChangedListeners.remove(listener);
    }

    private void updateViewOnSettingsChangedEventDone() {
        for (int i = 0; i < this.settingsChangedListeners.size(); i++){
            this.settingsChangedListeners.get(i).onSettingsChanged();
        }
    }

    public void changeExpenseTextColor(){
        updateViewOnSettingsChangedEventDone();
    }
}
