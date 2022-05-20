package com.example.qldanhba;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    ListView lsvSDT;
    EditText editTextTimKiem;
    FloatingActionButton btnThemMoiNguoiDung;
    ArrayList<NguoiDung>arrayListNguoiDung;
    NguoiDungAdapter nguoiDungAdapter;
    DataBase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lsvSDT= (ListView)findViewById(R.id.listViewDanhBa);
        editTextTimKiem=(EditText)findViewById(R.id.editTextTimKiem);
        btnThemMoiNguoiDung=(FloatingActionButton)findViewById(R.id.buttonFloatAdd);
        arrayListNguoiDung= new ArrayList<>();
        nguoiDungAdapter= new NguoiDungAdapter(this, R.layout.item_user_list,arrayListNguoiDung);
        lsvSDT.setAdapter(nguoiDungAdapter);
        db= new DataBase(this, "danhba.sqlite", null,1);
        db.QueryData("CREATE TABLE  IF NOT EXISTS NguoiDung(ID INTEGER  PRIMARY KEY  AUTOINCREMENT,TenNguoiDung VARCHAR(200),SDT VARCHAR(200))");
        show("");
        btnThemMoiNguoiDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogThem();
            }
        });
        editTextTimKiem.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                arrayListNguoiDung.clear();
                show(editTextTimKiem.getText().toString());
                return false;
            }
        });
        lsvSDT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NguoiDung nguoiDung= arrayListNguoiDung.get(i);
                dialogThaoTac(nguoiDung.getSdtNguoiDung());
            }
        });

    }

    private  void show(String text){
        Cursor dataCV= db.getData("SELECT * FROM NguoiDung WHERE TenNguoiDung like '%"+text +"%' OR SDT like '%"+text+"%'");
        arrayListNguoiDung.clear();
        float tong=0;
        while(dataCV.moveToNext()){
            String ten=dataCV.getString(1);
            String sdt=dataCV.getString(2);


            int id= dataCV.getInt(0);
            arrayListNguoiDung.add(new NguoiDung(id,ten,sdt));
        }

        nguoiDungAdapter.notifyDataSetChanged();
    }
    private  void dialogThaoTac(String sdt){
        Dialog dialog= new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_chucnang);
        Button btnCall= dialog.findViewById(R.id.buttonCall);
        Button btnMess= dialog.findViewById(R.id.buttonChat);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dial = "tel:" + sdt;
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
            }
        });
        btnMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent();
                intent.setAction(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("sms:"+sdt));
                startActivity(intent);
            }
        });
        dialog.show();
    }
    private  void dialogThem(){
        Dialog dialog= new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_them_nguoidung);

        EditText edtten=dialog.findViewById(R.id.editTextThemTen);
        EditText edtSDT=dialog.findViewById(R.id.editTextThemSDT);

        Button btnThem= dialog.findViewById(R.id.buttonThem);
        Button btnHuy=dialog.findViewById(R.id.buttonHuy);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenCV= edtSDT.getText().toString();
                if(tenCV.equals("")){
                    Toast.makeText(MainActivity.this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
                }else{
                    db.QueryData("INSERT INTO NguoiDung VALUES(null,'"+edtten.getText().toString()+"','"+edtSDT.getText().toString()+"' )");
                    dialog.dismiss();
                    show("");
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public  void DialogXoa(String tenCv,final int id){
        AlertDialog.Builder dialogXoa= new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn có muốn xóa "+ tenCv +" không");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                db.QueryData("DELETE FROM NguoiDung WHERE ID=" +id);
                Toast.makeText(MainActivity.this, "Đã Xóa", Toast.LENGTH_SHORT).show();
                show("");
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogXoa.show();
    }


    public  void DialogSuaCongViec(int id, String ten,String sdt){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_chinhsua_nguoidung);
        EditText txtSuaTen=  (EditText) dialog.findViewById(R.id.editTextTChinhSuaTen);
        EditText txtSDT=  (EditText) dialog.findViewById(R.id.editTextChinhSuaSDT);

        Button btnCapNhat=(Button) dialog.findViewById(R.id.buttonCapNhat);
        Button btnHuy=(Button)dialog.findViewById(R.id.buttonChinhSuaHuy);
        txtSuaTen.setText(ten);
        txtSDT.setText(sdt);


        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenMoi=txtSuaTen.getText().toString();
                String sdtMoi=txtSDT.getText().toString();
                db.QueryData("UPDATE NguoiDung SET SDT='"+txtSDT.getText().toString()+"',TenNguoiDung='"+txtSuaTen.getText().toString()+"' WHERE ID= "+id);

                Toast.makeText(MainActivity.this, "Đã cập nhật", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                show("");
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}