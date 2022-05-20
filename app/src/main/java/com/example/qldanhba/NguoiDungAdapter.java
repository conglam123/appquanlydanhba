package com.example.qldanhba;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class NguoiDungAdapter extends BaseAdapter {
    private MainActivity context;
    private  int layout;
    private List<NguoiDung> nguoiDungList;

    public NguoiDungAdapter(MainActivity context, int layout, List<NguoiDung> nguoiDungList) {
        this.context = context;
        this.layout = layout;
        this.nguoiDungList = nguoiDungList;
    }

    @Override
    public int getCount() {
        return nguoiDungList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    private class ViewHolder{
        TextView txtTen,txtSDT;
        ImageView imgEdit,imgDel;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            holder= new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(layout,null);
            holder.txtTen   =(TextView) view.findViewById(R.id.textViewTen);
            holder.txtSDT   =(TextView) view.findViewById(R.id.textViewSDT);

            holder.imgDel   =(ImageView) view.findViewById(R.id.buttonRemove);
            holder.imgEdit  =(ImageView) view.findViewById(R.id.buttonEdit);
            view.setTag(holder);
        }else{
            holder=(ViewHolder) view.getTag();
        }
        NguoiDung nguoiDung= nguoiDungList.get(i);
        holder.txtTen.setText(""+nguoiDung.getTenNguoiDung());
        holder.txtSDT.setText(""+nguoiDung.getSdtNguoiDung());


        //xóa
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.DialogSuaCongViec(nguoiDung.getId(),nguoiDung.getTenNguoiDung(),nguoiDung.getSdtNguoiDung());
            }
        });
        holder.imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.DialogXoa(nguoiDung.getTenNguoiDung(),nguoiDung.getId());
            }
        });
        return view;
    }
}
