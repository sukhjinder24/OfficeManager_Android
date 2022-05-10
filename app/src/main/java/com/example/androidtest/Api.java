package com.example.androidtest;

import com.example.androidtest.Entites.EmployeeEntity;
import com.example.androidtest.Entites.TaskEntity;
import com.example.androidtest.Models.EmployeeModel;
import com.example.androidtest.Models.PageResponse;
import com.example.androidtest.Models.TaskModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Api {

    String BASE_URL = "http://192.168.43.15:8084";

    @GET("Hello/webresources/employees")
    Call<PageResponse<EmployeeModel>> getEmployeeModels();

    @GET("Hello/webresources/employees/{empId}")
    Call<EmployeeEntity> getEmployeeByID(@Path(value = "empId") String empId);

    @POST("Hello/webresources/employees")
    Call<EmployeeEntity> createEmployee(@Body EmployeeEntity entity);

    @DELETE("Hello/webresources/employees/delete/{empId}")
    Call<String> deleteEmployee(@Path(value = "empId") String empId);

    @PUT("Hello/webresources/employees/attend/{empId}")
    Call<String> markAttendance(@Path(value = "empId") String empId);

    @POST("Hello/webresources/employees/login")
    Call<EmployeeEntity> login(@Body String username);

    @PUT("Hello/webresources/employees/update")
    Call<String> updateEmployee(@Body EmployeeEntity entity);

    @GET("Hello/webresources/employees/salary/{year}/{month}/{id}")
    Call<String> getSalary(@Path(value = "id") String id, @Path(value = "month") String month, @Path(value = "year") String year);


    @GET("Hello/webresources/tasks")
    Call<PageResponse<TaskModel>> getTaskModels();

    @GET("Hello/webresources/tasks/empId/{EmpId}")
    Call<PageResponse<TaskModel>> getTaskModelsByEmpId(@Path(value = "EmpId") String EmpId);

    @POST("Hello/webresources/tasks")
    Call<TaskEntity> createTask(@Body TaskEntity entity);


    @DELETE("Hello/webresources/tasks/delete/{taskId}")
    Call<String> deleteTask(@Path(value = "taskId") String taskId);

//
//    @Multipart
//    @POST("Hello/webresources/employees/dp")
//    Call<ResponseBody> postImage(@Part MultipartBody.Part image);


}
