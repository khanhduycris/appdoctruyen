package edu.fpt.apptruyentranh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import edu.fpt.apptruyentranh.data.TruyenTranh;

public class NoiDungTruyen extends AppCompatActivity {

    TruyenTranh truyenTranh;

    ImageView imgAnhTruyen;
    TextView tvTenTruyen;
    TextView tvTenTacGia;
    TextView tvNamSanXuat;
    TextView tvMoTa;
    Button btnDocTruyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noi_dung_truyen);


        imgAnhTruyen = findViewById(R.id.imgAnhTruyen);
        tvTenTruyen = findViewById(R.id.tvTenTruyen);
        tvTenTacGia = findViewById(R.id.tvTenTG);
        tvNamSanXuat = findViewById(R.id.tvNamSX);
        tvMoTa = findViewById(R.id.tvMoTa);

        Intent intent = getIntent();

        if (intent.getExtras() != null){
            truyenTranh = (TruyenTranh) intent.getSerializableExtra("data");
            tvTenTruyen.setText(truyenTranh.getTenTruyen());
            tvTenTacGia.setText(truyenTranh.getTenTacGia());
            tvNamSanXuat.setText(truyenTranh.getNamXuatBan());
            tvMoTa.setText(truyenTranh.getMoTaNgan());



            Glide.with(this).load(truyenTranh.getLinkAnh()).into(imgAnhTruyen);
        }

        btnDocTruyen = findViewById(R.id.btnDocTruyen);
        btnDocTruyen.setOnClickListener(v -> {
            startActivity(new Intent(NoiDungTruyen.this, DocTruyen.class));
        });
    }
}