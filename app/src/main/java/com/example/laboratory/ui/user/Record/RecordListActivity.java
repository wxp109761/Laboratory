package com.example.laboratory.ui.user.Record;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.DownloadListener;
import android.widget.Toast;
import androidx.core.content.FileProvider;
import com.example.laboratory.R;
import com.example.laboratory.bean.Record;
import com.example.laboratory.common.UrlConstainer;
import com.example.laboratory.manager.UserInfoManager;
import com.example.laboratory.root_ui.Dialog.filterDialog;
import com.example.laboratory.ui.adapter.BaseListAdapter;
import com.example.laboratory.ui.adapter.RecordListAdapter;
import com.example.laboratory.ui.base.BaseAbListActivity;

import com.example.laboratory.utils.FileUtils;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class RecordListActivity extends BaseAbListActivity<RecordPresenter, Record.RecordListBean> implements RecordContract.IRecordView, RecordListAdapter.OnRecordListItemClickListener {
    String url = "http://192.168.43.115:9001/xjrecord/getExcelRecord/78714756973858818/XX/xx/xx/all";
    int position;
    @Override
    protected boolean initToolbar() {
        mToolbar.setTitle(R.string.xj_records);
        return true;
    }
    @Override
    protected RecordPresenter createPresenter() {
        return new RecordPresenter();
    }

    @Override
    protected void loadDatas() {
        mPresenter.loadRecordList();
    }



    @Override
    public void setData(List<Record.RecordListBean> data) {
        mListData.clear();
        mListData.addAll(data);
    }

    @Override
    protected BaseListAdapter<Record.RecordListBean> getListAdapter() {
        return new RecordListAdapter(this);
    }

    //创建Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.right_menu_setting, menu);
        return super.onCreateOptionsMenu(menu);
    }
//    xjrecord/getExcelRecord/{uid}/{labId}/{date1}/{date2}/{cate}
    //Menu点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FileDownloader.setup(this);
        switch (item.getItemId()) {
            case R.id.menu_add_notice:
                downLoad(url, "巡检记录表.xls");
                final MaterialDialog dialog = new MaterialDialog(this);
                dialog.content(
                        "是否打开下载的文档")//
                        .btnText("取消", "确定")//
//                .showAnim(mBasIn)//
//                .dismissAnim(mBasOut)//
                        .show();

                dialog.setOnBtnClickL(
                        new OnBtnClickL() {//left btn click listener
                            @Override
                            public void onBtnClick() {

                                dialog.dismiss();
                            }
                        },
                        new OnBtnClickL() {//right btn click listener
                            @Override
                            public void onBtnClick() {
                                String fileUrl = Environment.getExternalStorageDirectory().toString() + "/laboratory/巡检记录表.xls";
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                File apkFile = new File(fileUrl);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                    Uri uri = FileProvider.getUriForFile(getBaseContext(), "com.example.laboratoryProvider", apkFile);
                                    intent.setDataAndType(uri, "application/vnd.ms-excel");
                                } else {
                                    intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.ms-excel");
                                }
                                startActivity(intent);
                                dialog.dismiss();
                            }
                        }
                );
                break;
        }
        return super.onOptionsItemSelected(item);
    }




    /**
     * 从服务器下载文件
     *
     * @param path     下载文件的地址
     * @param FileName 文件名字
     */
    public static void downLoad(final String path, final String FileName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(path);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setReadTimeout(5000);
                    con.setConnectTimeout(5000);
                    con.setRequestProperty("Charset", "UTF-8");
                    con.setRequestMethod("GET");
                    if (con.getResponseCode() == 200) {
                        InputStream is = con.getInputStream();//获取输入流
                        FileOutputStream fileOutputStream = null;//文件输出流
                        if (is != null) {
                            FileUtils fileUtils = new FileUtils();
                            fileOutputStream = new FileOutputStream(fileUtils.createFile(FileName));//指定文件保存路径，代码看下一步
                            byte[] buf = new byte[1024];
                            int ch;
                            while ((ch = is.read(buf)) != -1) {
                                fileOutputStream.write(buf, 0, ch);//将获取到的流写入文件中
                            }
                            System.out.println("下载成功");
                        }
                        if (fileOutputStream != null) {
                            fileOutputStream.flush();
                            fileOutputStream.close();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void continueDownLoad(BaseDownloadTask task) {
        while (task.getSmallFileSoFarBytes()!=task.getSmallFileTotalBytes()){
            int percent=(int) ((double) task.getSmallFileSoFarBytes() / (double) task.getSmallFileTotalBytes() * 100);
        }
    }

    @Override
    public void showResult(String msg) {

    }


    @Override
    public void onDeleteClick(int position, String xjid) {
        mPresenter.deleteXjRecordByXjId(xjid);
        mListData.remove(position);
        if(mListData.size()==0){
            showEmpty();
        }else {
            mRecyclerView.notifyDataSetChanged();
        }
    }



}
