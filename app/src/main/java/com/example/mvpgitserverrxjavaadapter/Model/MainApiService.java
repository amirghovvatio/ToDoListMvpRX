package com.example.mvpgitserverrxjavaadapter.Model;

import com.google.gson.JsonObject;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainApiService {
    private final Retrofit retrofit;
    private RetrofitService retrofitService;
    public MainApiService(){
         retrofit=new Retrofit.Builder()
                 .addConverterFactory(GsonConverterFactory.create())
                 .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                 .baseUrl("https://amir.vipmive.com/api/")
                 .build();
         retrofitService=retrofit.create(RetrofitService.class);
    }

    public Single<List<Days>> getAllDays(){
        return retrofitService.getAllDays();
    }
    public Single<Days> addDays(String dayName,String date){
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("dayName",dayName);
        jsonObject.addProperty("date",date);
        return retrofitService.addDays(jsonObject);
    }
    public Single<Boolean> deleteDay(int dayId){
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("id",dayId);
        return retrofitService.deleteDays(jsonObject);
    }

    public Single<List<Task>> getAllTask(int dayId){
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("id",dayId);
        return retrofitService.getAllTasks(jsonObject);
    }
    public Single<Task> addTask(int dayId,String title){
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("dayId",dayId);
        jsonObject.addProperty("title",title);
        return retrofitService.addTask(jsonObject);
    }
    public Single<Boolean> deleteTask(int id){
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("id",id);
        return retrofitService.deleteTasks(jsonObject);
    }
}
