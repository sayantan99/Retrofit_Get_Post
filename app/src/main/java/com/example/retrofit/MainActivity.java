package com.example.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView text;
    private Button but,pos;
    private EditText po;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.text);
        but = findViewById(R.id.button);
        po = findViewById(R.id.pppp);
        pos=findViewById(R.id.button2);

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getAllPosts();

            }
        });
        pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postMessage();
            }
        });
    }

    private void getAllPosts() {

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        clientBuilder.addInterceptor(interceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(clientBuilder.build())
                .build();

        int id = Integer.valueOf(po.getText().toString());

        PostEndPoint endPoint = retrofit.create(PostEndPoint.class);
        /*Call<List<post>> call= endPoint.getPosts(id);// after that we have to create walker thread for networking
        call.enqueue(new Callback<List<post>>() {
            @Override
            public void onResponse(Call<List<post>> call, Response<List<post>> response) {

                ArrayList<post> posts= (ArrayList<post>) response.body();
                for (post p: posts)
                {
                    text.setText(text.getText()+"\n\t"+p.getTitle());

                }
            }

            @Override
            public void onFailure(Call<List<post>> call, Throwable t) {

                t.printStackTrace();

            }
        }); */

      /* Call<post> call= endPoint.getSinglePost(id);
       call.enqueue(new Callback<post>() {
           @Override
           public void onResponse(Call<post> call, Response<post> response) {

               post po= response.body();
               text.setText(po.getTitle());
           }

           @Override
           public void onFailure(Call<post> call, Throwable t) {
            t.printStackTrace();
           }
       });*/

        Call<List<post>> call = endPoint.getPostByUserId(id);
        call.enqueue(new Callback<List<post>>() {
            @Override
            public void onResponse(Call<List<post>> call, Response<List<post>> response) {


                ArrayList<post> posts = (ArrayList<post>) response.body();

                for (post p : posts) {
                    text.setText(text.getText() + "\n\t" + p.getTitle());
                }

            }

            @Override
            public void onFailure(Call<List<post>> call, Throwable t) {
                t.printStackTrace();

            }
        });

        /*//For posting message on web server
        post post = new post(102, "New post", "the body of new post");
        String token="sfhjadsgf389ry";
        Call<post> call = endPoint.newpost(token,post);
        call.enqueue(new Callback<com.example.retrofit.post>() {
            @Override
            public void onResponse(Call<com.example.retrofit.post> call, Response<com.example.retrofit.post> response) {
                Toast.makeText(MainActivity.this,"Code : "+response.code(),Toast.LENGTH_SHORT).show();
                if(response.isSuccessful())
                {
                    post newPost= response.body();
                    text.setText(newPost.getBody());
                }

            }
            //Code 201 means we have successfully posted the new post to the web server
            @Override
            public void onFailure(Call<com.example.retrofit.post> call, Throwable t) {

            }
        });*/
    }
    public void postMessage()
    {
        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())

                .build();
        PostEndPoint endPoint = retrofit2.create(PostEndPoint.class);
        post post = new post(102, "New post", "the body of new post");
        String token="sfhjadsgf389ry";
        Call<post> call = endPoint.newpost(token,post);
        call.enqueue(new Callback<com.example.retrofit.post>() {
            @Override
            public void onResponse(Call<com.example.retrofit.post> call, Response<com.example.retrofit.post> response) {
                Toast.makeText(MainActivity.this,"Code : "+response.code(),Toast.LENGTH_SHORT).show();
                if(response.isSuccessful())
                {
                    post newPost= response.body();
                    text.setText(newPost.getBody());
                }

            }
            //Code 201 means we have successfully posted the new post to the web server
            @Override
            public void onFailure(Call<com.example.retrofit.post> call, Throwable t) {

            }
        });
    }
}