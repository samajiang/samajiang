package com.example.android.Fragment;

import static android.app.ProgressDialog.show;
import static android.content.Context.MODE_PRIVATE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.android.tool.BASEURL;
import com.example.android.LoginActivity;
import com.example.android.MainActivity;
import com.example.android.R;
import com.example.android.UpdateUserActivity;
import com.example.android.tool.SessionManager;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MineFragment extends Fragment {
    private Button denglubtn;

    private SharedPreferences sharedPreferences2;
    private TextView userID;

    private TextView phonenum;

    private ImageView touxiang;
    private TextView healthManage, doctorAdvice, updateUser;
    private Button loginOut;
    private SessionManager sessionManager;

    private ActivityResultLauncher<Intent> pickImage;

//    private static final String BASE_URL = "http://192.168.1.101:8080"; // 替换为你的服务器地址

    private static final String API_PATH = "admin/avatar/%s";
    private String username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        // Inflate the layout for this fragment
//        初始化控件的地方
        sharedPreferences2 = getActivity().getSharedPreferences("user", MODE_PRIVATE);

        sessionManager = new SessionManager(getContext());
        username = sharedPreferences2.getString("username", null);
        phonenum = view.findViewById(R.id.userPhone);
        userID = view.findViewById(R.id.userID);
        touxiang = view.findViewById(R.id.usertouxiang);
        updateUser = view.findViewById(R.id.update_user);
        loginOut = view.findViewById(R.id.loginout);
        denglubtn = view.findViewById(R.id.denglu);

        loadAvatarFromServer();

        if(sharedPreferences2.getString("username",null)==null){
            loginOut.setVisibility(INVISIBLE);
            denglubtn.setVisibility(VISIBLE);
        }
        else{
            loginOut.setVisibility(VISIBLE);
            denglubtn.setVisibility(INVISIBLE);
        }

        denglubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        /**
         * 图片选择结果处理器
         * 使用新的ActivityResult API，替代旧的onActivityResult回调
         * 当用户选择完图片后会触发这个处理器
         */
        pickImage = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getData() != null) {
                        // 获取选择的图片URI
                        Uri imageUri = result.getData().getData();
                        // 使用Glide加载图片
                        loadImage(imageUri);
                    }
                });

//打开相册选择图片作为头像
        touxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });


//        退出登录按钮
        loginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                @SuppressLint("ResourceType") AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                        .setTitle("退出登录")
                        .setMessage("退出账户会清空您的账户信息，您确定要退出吗？")
                        .setPositiveButton("确定",(dialogInterface, i) -> {
                            //             清空sharedprefernces的用户名和登录态
                            sessionManager.logout();
                            SharedPreferences.Editor editor = sharedPreferences2.edit();
                            editor.clear();
                            editor.commit();
                            ((MainActivity)getActivity()).restartApp();
                            MainActivity mainActivity = (MainActivity) getActivity();
                            mainActivity.selectFragment(0);
                            MenuItem menuItem = mainActivity.bottomNavigationView.getMenu().findItem(R.id.home);
                            mainActivity.bottomNavigationView.performClick();
                            menuItem.setChecked(true);
                            loginOut.setVisibility(INVISIBLE);
                            denglubtn.setVisibility(VISIBLE);
                            touxiang.setImageResource(R.mipmap.ic_launcher);
                            userID.setText("null");
                            phonenum.setText("null");


                        })
                       .setNegativeButton("取消",null)
                       .show();
            }
        });

//        信息修改页面跳转
        updateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UpdateUserActivity.class);
                startActivity(intent);
            }
        });


//        从登录界面保存的账号中取得用户名字
        String id = sharedPreferences2.getString("username", null);
        String phone = sharedPreferences2.getString("phone", null);
        phonenum.setText(phone);
        userID.setText(id);

        return view;
    }


    /**
     * 使用Glide加载并显示图片
     *
     * @param imageUri 图片的URI
     */
    private void loadImage(Uri imageUri) {
        Glide.with(this)  // 设置Context
                .load(imageUri)  // 设置图片源
                .apply(new RequestOptions()
                        .error(R.drawable.ic_launcher_background)  // 设置加载失败显示的图片
                        .centerCrop())  // 居中裁剪
                .into(touxiang);  // 设置显示的ImageView

        // 上传图片到服务器
        updateavatar(imageUri);
    }

    /**
     * 权限请求处理器
     * 用于处理存储权限的请求结果
     * Android 6.0及以上版本需要动态申请权限
     */
    private final ActivityResultLauncher<String> requestPermission = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            isGranted -> {
                if (isGranted) {
                    // 用户授予权限，打开相册
                    openGallery();
                } else {
                    // 用户拒绝权限，显示提示
                    Toast.makeText(getActivity(), "需要存储权限来选择图片", Toast.LENGTH_SHORT).show();
                }
            });


    /**
     * 检查并请求存储权限
     * 如果已经有权限就直接打开相册
     * 如果没有权限就请求权限
     */
    private void checkPermissionAndPickImage() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            // 已有权限，直接打开相册
            openGallery();
        } else {
            // 请求权限
            requestPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    /**
     * 打开系统相册
     * 使用Intent.ACTION_PICK启动系统相册选择图片
     */
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickImage.launch(intent);
    }


    /**
     * 上传图片到服务器
     */
    private void updateavatar(Uri imageUri) {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("正在上传头像...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        try {
            // 从 Uri 创建临时文件
            File tempFile = createTempFileFromUri(imageUri);
            if (tempFile == null) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "文件处理失败", Toast.LENGTH_SHORT).show();
                return;
            }

            // 创建 OkHttpClient
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();

            // 创建请求体
            RequestBody requestBody = new MultipartBody.Builder()
//                    设置为多部分表单类型
                    .setType(MultipartBody.FORM)
//                    从刚刚创建的临时文件中添加头像文件
                    .addFormDataPart("avatar" // 文件字段名
                            , tempFile.getName(),// 文件名
                            RequestBody.create(
                                    // 获取文件的MIME类型
                                    MediaType.parse
                                            (getActivity().getContentResolver().getType(imageUri)), // 如: image/jpeg
                                    tempFile // 临时文件对象
                            ))
                    // 添加用户标识
                    .addFormDataPart("userId", // 用户ID字段名
                            sharedPreferences2.getString("username", null)) // 从SharedPreferences获取用户名，如果不存在返回null
                    .build();

            // 创建请求
            Request request = new Request.Builder()
                    .url(BASEURL.getBase_URL()+"admin/upload/avatar")
                    .post(requestBody)
                    .build();

            // 执行请求
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    getActivity().runOnUiThread(() -> {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(),
                                "上传失败: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    getActivity().runOnUiThread(() -> {
                        progressDialog.dismiss();
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(),
                                    "上传成功",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(),
                                    "上传失败: " + response.message(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

        } catch (Exception e) {
            progressDialog.dismiss();
            Toast.makeText(getContext(),
                    "上传失败: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 从Uri创建临时文件
     */
    private File createTempFileFromUri(Uri uri) throws IOException {
        // 获取 ContentResolver 用于访问文件系统
        ContentResolver contentResolver = getActivity().getContentResolver();
        // 从 Uri 中获取文件名
        String fileName = getFileName(contentResolver, uri);

        // 创建临时文件
        // 在应用的缓存目录下创建临时文件
        // getCacheDir() 返回的目录在应用卸载时会被自动清理
        File tempFile = new File(getActivity().getCacheDir(), fileName);

        // 复制文件内容
        // 使用 try-with-resources 确保流会被正确关闭
        try (
                // 打开 Uri 对应文件的输入流
                InputStream is = contentResolver.openInputStream(uri);
                // 创建临时文件的输出流
                OutputStream os = new FileOutputStream(tempFile)) {
            // 如果无法打开输入流，返回 null
            if (is == null) return null;
            // 创建缓冲区，用于批量读写数据
            // 4KB 的缓冲区大小，可以平衡内存使用和性能
            byte[] buffer = new byte[4096];
            // 循环读取数据直到文件末尾
            // read() 返回 -1 表示已到达文件末尾
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                // 将读取的数据写入临时文件
                // 只写入实际读取的字节数
                os.write(buffer, 0, bytesRead);
            }
            // 确保所有数据都写入到文件
            os.flush();
            // 返回创建的临时文件
            return tempFile;
        }
        // try-with-resources 会自动关闭 InputStream 和 OutputStream
    }

    /**
     * 从 Uri 获取文件名
     * 尝试从 Uri 的 ContentResolver 查询或 Uri 路径中获取文件名
     *
     * @param contentResolver 用于查询 Uri 信息的 ContentResolver
     * @param uri             需要获取文件名的 Uri
     * @return 提取的文件名，如果无法获取原文件名则生成一个唯一的文件名
     */
    private String getFileName(ContentResolver contentResolver, Uri uri) {
        String result = null;
        // 如果是 content:// 类型的 Uri
        if (uri.getScheme().equals("content")) {
            // 尝试从 ContentResolver 查询获取文件名
            try (Cursor cursor = contentResolver.query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    // 获取显示名称列的索引
                    int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (index >= 0) {
                        result = cursor.getString(index);
                    }
                }
            }
        }
        // 如果通过 ContentResolver 无法获取文件名，则从 Uri 路径中提取
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        // 确保文件名唯一，避免覆盖已有文件
        String extension = "";
        int i = result.lastIndexOf('.');
        if (i > 0) {
            extension = result.substring(i);
            result = result.substring(0, i);
        }
        // 添加时间戳确保文件名唯一
        return result + "_" + System.currentTimeMillis() + extension;
    }

    /**
     * 从服务器获取并显示头像
     */
    private void loadAvatarFromServer() {
        // 创建 OkHttpClient
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        // 创建请求
        // 构建完整的URL
        // 构建请求URL和请求对象
        // 使用 String.format 将用户ID插入到API路径中
        String fullUrl = String.format(BASEURL.getBase_URL() + API_PATH, username);
        Request request = new Request.Builder()
                .url(fullUrl)
                .get()
                .build();
        Log.d("abc", request.toString());

        // 执行请求
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(),
                            "获取头像失败: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    // 加载失败时显示默认头像
                    touxiang.setImageResource(R.mipmap.ic_launcher);
                    ;
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        // 解析响应数据
                        String responseData = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseData);
                        // 从JSON中获取头像URL
                        String avatarUrl = jsonObject.getString("avatarUrl");

                        // 在主线程中使用Glide加载图片
                        // 拼接完整的图片URL并加载
                        getActivity().runOnUiThread(() -> loadAvatarWithGlide(BASEURL.getTouxiangurl() + avatarUrl));
                    } catch (Exception e) {
                        getActivity().runOnUiThread(() -> {
                            Toast.makeText(getContext(),
                                    "解析头像数据失败: " + e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            touxiang.setImageResource(R.mipmap.ic_launcher);
                        });
                    }
                } else {
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(),
                                "获取头像失败la: " + response.message() + response.code(),
                                Toast.LENGTH_SHORT).show();
                        touxiang.setImageResource(R.mipmap.ic_launcher);
                    });
                }
            }
        });
    }

    /**
     * 使用Glide加载头像
     */
    private void loadAvatarWithGlide(String avatarUrl) {
        Glide.with(this)
                .load(avatarUrl)
                .apply(new RequestOptions() // 应用图片加载选项
                        .diskCacheStrategy(DiskCacheStrategy.ALL) // 设置磁盘缓存策略
                        .centerCrop())// 设置图片裁剪方式
                        .placeholder(R.mipmap.ic_launcher)
                .into(touxiang); // 设置目标ImageView
        Log.d("avatarurl", avatarUrl + "");
    }

}