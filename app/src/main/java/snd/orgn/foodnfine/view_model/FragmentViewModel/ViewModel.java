package snd.orgn.foodnfine.view_model.FragmentViewModel;


import android.os.Parcel;
import android.os.Parcelable;

import android.util.Log;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;

import io.reactivex.disposables.CompositeDisposable;


public abstract class ViewModel extends BaseObservable {



    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public CompositeDisposable getCompositeDisposableDestroyOnPause(){
        return compositeDisposableDestroyOnPause;
    }



    private CompositeDisposable compositeDisposable;
    private CompositeDisposable compositeDisposableDestroyOnPause;

    protected ViewModel(@Nullable State savedInstanceState) {
        compositeDisposable=new CompositeDisposable();
        compositeDisposableDestroyOnPause=new CompositeDisposable();
    }
    protected ViewModel(@Nullable State savedInstanceState, CompositeDisposable compositeDisposable) {
        this.compositeDisposable=compositeDisposable;
    }

    @CallSuper
    public void onStart() {
        Log.d("OkHttp","Viewmodel On start called");
        if(compositeDisposable==null||compositeDisposable.isDisposed()){
            compositeDisposable=new CompositeDisposable();
            compositeDisposableDestroyOnPause.size();
            Log.d("OkHttp","new CompositeDisposable()");
            Log.d("OkHttp","compositeDisposableDestroyOnPause.size() "+compositeDisposableDestroyOnPause.size());
        }

    }

    public State getInstanceState() {
        return new State(this);
    }

    @CallSuper
    public void onStop() {
        Log.d("OkHttp","Viewmodel On stop called");
        if (compositeDisposable!=null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
            Log.d("OkHttp","compositeDisposable disposed");
        }
    }
    //    @CallSuper
//    public void onDestroy(){
//
//    }
    public void onPause(){
        Log.d("OkHttp","Viewmodel On Pause called");
        if (compositeDisposableDestroyOnPause!=null && !compositeDisposableDestroyOnPause.isDisposed()) {
            Log.d("OkHttp","BEFORE DISPOSING compositeDisposableDestroyOnPause.size() "+compositeDisposableDestroyOnPause.size());
            compositeDisposableDestroyOnPause.size();
            compositeDisposableDestroyOnPause.dispose();
            Log.d("OkHttp","compositeDisposableDestroyOnPause disposed");
            Log.d("OkHttp","AFTER DISPOSING compositeDisposableDestroyOnPause.size() "+compositeDisposableDestroyOnPause.size());
        }
    }

    public static class State implements Parcelable {

        protected State(ViewModel viewModel) {

        }

        public State(Parcel in) {

        }

        @Override
        public int describeContents() {
            return 0;
        }

        @CallSuper
        @Override
        public void writeToParcel(Parcel dest, int flags) {

        }

        public static Creator<State> CREATOR = new Creator<State>() {
            @Override
            public State createFromParcel(Parcel source) {
                return new State(source);
            }

            @Override
            public State[] newArray(int size) {
                return new State[size];
            }
        };
    }

}
