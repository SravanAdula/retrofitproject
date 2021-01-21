 package com.example.retrofittrial;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.retrofittrial.api_interfaces.JsonPlaceHolderApi;
import com.example.retrofittrial.models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

 public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;
    EditText name,address,score;

    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewResult=findViewById(R.id.text_view_result);
        name=findViewById(R.id.name);
        address=findViewById(R.id.address);
        score=findViewById(R.id.score);
        btn=findViewById(R.id.btn);
        getPosts();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a=name.getText().toString().trim();
                String b=address.getText().toString().trim();
                String value= score.getText().toString();
                int c=Integer.parseInt(value);
            createPost(a,b,c);
            }
        });
    }

    void createPost(String x,String y,int z) {

        Post post=new Post(x,y,z);
        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        JsonPlaceHolderApi jsonPlaceHolderApi=retrofit.create(JsonPlaceHolderApi.class);
        Call<Post> call=jsonPlaceHolderApi.createPost(post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Toast.makeText(MainActivity.this, "details sent to database!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
            }
        });
     }


    public void getPosts(){
        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        JsonPlaceHolderApi jsonPlaceHolderApi=retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Post>> call=jsonPlaceHolderApi.getPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("Code: " +response.code());
                    return;
                }
                List<Post> posts=response.body();
                for (Post post:posts)
                {
                    String content="";
                    content+="name: " + post.getName()+"\n";
                    content+="address: " + post.getAddress() +"\n";
                    content+="score: " + post.getScore() +"\n\n";
                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
}