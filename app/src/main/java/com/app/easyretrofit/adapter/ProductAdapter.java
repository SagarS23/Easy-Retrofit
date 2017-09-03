package com.app.easyretrofit.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.easyretrofit.R;
import com.app.easyretrofit.model.ProductRes;
import com.app.easyretrofit.widget.CustomTextViewRegular;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    Context context;
    List<ProductRes> productRes;

    //Constructor
    public ProductAdapter(Context context, List<ProductRes> productRes) {
        this.context = context;
        this.productRes = productRes;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        //Set data to views from Model class
        holder.tvTitle.setText(productRes.get(position).getTitle());
        holder.tvDetails.setText(productRes.get(position).getBody());

    }

    @Override
    public int getItemCount() {
        return productRes.size();  //Return data
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_title)
        CustomTextViewRegular tvTitle;
        @Bind(R.id.tv_details)
        CustomTextViewRegular tvDetails;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView); //ButterKnief dependency injection
        }
    }
}
