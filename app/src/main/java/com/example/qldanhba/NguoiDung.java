package com.example.qldanhba;

public class NguoiDung {
    public int id;
    public String tenNguoiDung, sdtNguoiDung;

    public NguoiDung(int id, String tenNguoiDung, String sdtNguoiDung) {
        this.id = id;
        this.tenNguoiDung = tenNguoiDung;
        this.sdtNguoiDung = sdtNguoiDung;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenNguoiDung() {
        return tenNguoiDung;
    }

    public void setTenNguoiDung(String tenNguoiDung) {
        this.tenNguoiDung = tenNguoiDung;
    }

    public String getSdtNguoiDung() {
        return sdtNguoiDung;
    }

    public void setSdtNguoiDung(String sdtNguoiDung) {
        this.sdtNguoiDung = sdtNguoiDung;
    }
}
