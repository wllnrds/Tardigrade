package tardigrade.resources.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.os.Build;

import java.util.UUID;

import tardigrade.Tardigrade;
import tardigrade.comunication.IDiscovery;
import tardigrade.comunication.INetwork;
import tardigrade.utils.Flag;
import tardigrade.utils.ICallback;

@SuppressLint("NewApi")
public class Discovery implements IDiscovery {
    private INetwork network;

    private String SERVICE_TYPE = "_Tardigrade._tcp.";
    private String SERVICE_UID = UUID.randomUUID().toString();
    private String SERVICE_NAME = Build.MODEL;

    private NsdManager mNsdManager;
    private NsdServiceInfo mNsdServiceInfo;

    private boolean init = false;
    private boolean isRegisterWorking = false;
    private boolean isDiscoveryWorking = false;

    private ICallback onStart = null;
    private ICallback onFound = null;
    private ICallback onLost = null;
    private ICallback onStop = null;
    private ICallback onFailStart = null;

    private static Discovery ourInstance = new Discovery();
    public static Discovery getInstance() {
        return ourInstance;
    }

    private Discovery() {
        onStart = new NullCallback();
        onFound = new NullCallback();
        onLost = new NullCallback();
        onStop = new NullCallback();
        onFailStart = new NullCallback();

        mNsdServiceInfo = new NsdServiceInfo();
        mNsdServiceInfo.setServiceName(getServiceRef());
        mNsdServiceInfo.setServiceType(SERVICE_TYPE);
        mNsdServiceInfo.setPort(0);
    }

    public void setPin(String pin){
        SERVICE_TYPE = "_Tardigrade" + pin + "._tcp.";
    }
    public void setServiceId(String id){
        SERVICE_UID = id;
        mNsdServiceInfo.setServiceName(SERVICE_UID + ":" + SERVICE_NAME);
    }
    public void setServiceName(String name){
        SERVICE_NAME = name;
        mNsdServiceInfo.setServiceName(SERVICE_UID + ":" + SERVICE_NAME);
    }
    public String getServiceRef(){
        return SERVICE_UID + ":" + SERVICE_NAME;
    }
    public String getPin(){
        return SERVICE_TYPE.split(".")[1];
    }
    public String getServiceName(){
        return SERVICE_NAME;
    }
    public String getServiceId(){
        return SERVICE_UID;
    }

    @Override
    public void init() {
        Tardigrade root = Tardigrade.getInstance(null);

        if(root != null){
            mNsdManager = (NsdManager) root.getContext().getSystemService(Context.NSD_SERVICE);
            this.network = root.mNetwork;
        }

        init = true;
    }

    @Override
    public boolean start() {
        if(!init){
            init();
        }

        if(!network.isWorking()) {
            network.start();
        }

        if(!isWorking()){
            mNsdServiceInfo.setServiceName(getServiceRef());
            mNsdServiceInfo.setServiceType(SERVICE_TYPE);
            mNsdServiceInfo.setPort(network.getPort());

            mNsdManager.registerService(mNsdServiceInfo, NsdManager.PROTOCOL_DNS_SD, mRegister);
            mNsdManager.discoverServices(SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, mDiscovery);

            onStart.doit(Pack.create(Flag.RESULT_OK, true));
            return true;
        }
        return false;
    }
    @Override
    public void stop() {
        if(!init && !isWorking()){
            return;
        }

        try{
            mNsdManager.stopServiceDiscovery(mDiscovery);
            mNsdManager.unregisterService(mRegister);
            onStop.doit(Pack.create(Flag.RESULT_OK, true));
        }catch (Exception e){ }

        isDiscoveryWorking = false;
        isRegisterWorking = false;
    }

    @Override
    public void setOnStartService(ICallback callback){
        onStart = callback;
    }
    @Override
    public void setOnStopService(ICallback callback){
        onStop = callback;
    }
    @Override
    public void setOnFailService(ICallback callback){
        onFailStart = callback;
    }

    @Override
    public void setOnFoundDevice(ICallback callback){

        onFound = callback;
    }
    @Override
    public void setOnLostDevice(ICallback callback){
        onLost = callback;
    }


    @Override
    public boolean isWorking() {
        if(!init) {
            return false;
        }

        return isDiscoveryWorking && isRegisterWorking;
    }

    private NsdManager.RegistrationListener mRegister = new NsdManager.RegistrationListener(){
        @Override
        public void onRegistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
            isRegisterWorking = false;
        }
        @Override
        public void onServiceRegistered(NsdServiceInfo serviceInfo) {
            isRegisterWorking = true;
        }
        @Override
        public void onServiceUnregistered(NsdServiceInfo serviceInfo) {
            isRegisterWorking = false;
        }
        @Override
        public void onUnregistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
            isRegisterWorking = true;
        }
    };
    private NsdManager.DiscoveryListener mDiscovery = new NsdManager.DiscoveryListener() {
        @Override
        public void onServiceFound(NsdServiceInfo serviceInfo) {
            mNsdManager.resolveService(serviceInfo, new NsdManager.ResolveListener() {
                @Override
                public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {

                }
                @Override
                public void onServiceResolved(NsdServiceInfo serviceInfo) {
                    if(!serviceInfo.getServiceName().equals(getServiceRef())) {
                        Channel mChannel = new Channel(
                                serviceInfo.getServiceName(),
                                serviceInfo.getHost(),
                                serviceInfo.getPort());
                        onFound.doit(Pack.create(Flag.NOTIFY, mChannel));
                    }
                }
            });
        }
        @Override
        public void onServiceLost(NsdServiceInfo serviceInfo) {
            Channel mChannel = new Channel(
                    serviceInfo.getServiceName(),
                    serviceInfo.getHost(),
                    serviceInfo.getPort());
            onLost.doit(Pack.create(Flag.NOTIFY, mChannel));
        }

        @Override
        public void onDiscoveryStarted(String serviceType) {
            isDiscoveryWorking = true;
        }
        @Override
        public void onDiscoveryStopped(String serviceType) {
            isDiscoveryWorking = false;
        }
        @Override
        public void onStartDiscoveryFailed(String serviceType, int errorCode) {
            onFailStart.doit(Pack.create(Flag.NOTIFY, errorCode));
            isDiscoveryWorking = false;
        }
        @Override
        public void onStopDiscoveryFailed(String serviceType, int errorCode) {
            isDiscoveryWorking = true;
        }
    };

    public String getIP() {
        return mNsdServiceInfo.getHost() + ":" + mNsdServiceInfo.getPort();
    }
}
