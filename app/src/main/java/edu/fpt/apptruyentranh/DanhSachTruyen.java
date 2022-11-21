package edu.fpt.apptruyentranh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import edu.fpt.apptruyentranh.api.Api;
import edu.fpt.apptruyentranh.data.TruyenTranh;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachTruyen extends AppCompatActivity {

    private List<TruyenTranh> truyenTranhList = new ArrayList<>();

    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_truyen);

        gridView = findViewById(R.id.gridView);

        getAllTruyenTranh();


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(DanhSachTruyen.this, NoiDungTruyen.class)
                        .putExtra("data", truyenTranhList.get(position)));
            }
        });
    }

    public void  getAllTruyenTranh(){
        Call<List<TruyenTranh>> truyenTranh = Api.getInterface().getAllTruyenTranh();

        truyenTranh.enqueue(new Callback<List<TruyenTranh>>() {
            @Override
            public void onResponse(Call<List<TruyenTranh>> call, Response<List<TruyenTranh>> response) {
                if (response.isSuccessful()){
                    String message = "Lấy data thành công";
                    Toast.makeText(DanhSachTruyen.this, message, Toast.LENGTH_SHORT).show();

                    truyenTranhList = response.body();

                    TruyenTranhAdapter truyenTranhAdapter = new TruyenTranhAdapter(truyenTranhList, DanhSachTruyen.this);
                    gridView.setAdapter(truyenTranhAdapter);

                }else {
                    String message = "Error";
                    Toast.makeText(DanhSachTruyen.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TruyenTranh>> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(DanhSachTruyen.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }



    public class TruyenTranhAdapter extends BaseAdapter {

        private List<TruyenTranh> truyenTranhList;
        private Context context;
        private LayoutInflater layoutInflater;

        public TruyenTranhAdapter(List<TruyenTranh> truyenTranhList, Context context) {
            this.truyenTranhList = truyenTranhList;
            this.context = context;
            this.layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return truyenTranhList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                convertView = layoutInflater.inflate(R.layout.item_list_truyen, parent, false);
            }
            ImageView imgAnhTruyen = convertView.findViewById(R.id.imgAnhTruyen);
            TextView tvTenTruyen = convertView.findViewById(R.id.tvTenTruyen);
            TextView tvTenTacGia = convertView.findViewById(R.id.tvTenTG);
            TextView tvNamSanXuat = convertView.findViewById(R.id.tvNamSX);
//            TextView tvMoTa = convertView.findViewById(R.id.tvMoTa);

            tvTenTruyen.setText(truyenTranhList.get(position).getTenTruyen());
            tvTenTacGia.setText(truyenTranhList.get(position).getTenTacGia());
            tvNamSanXuat.setText(truyenTranhList.get(position).getNamXuatBan());
//            tvMoTa.setText(truyenTranhList.get(position).getMoTaNgan());

            Glide.with(context)
                    .load(truyenTranhList.get(position).getLinkAnh())
                    .into(imgAnhTruyen);

            return convertView;
        }
    }
}