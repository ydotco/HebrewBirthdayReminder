package com.ydotco.hebrewbirthdayreminder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder>{
    LayoutInflater inflater;
    List<Contact> contacts= Collections.emptyList();
    public ContactAdapter(Context context,List<Contact> contacts) {
        inflater = LayoutInflater.from(context);
        this.contacts=contacts;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.contact_item, parent, false);
        return  new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        Contact contact=contacts.get(position);
        holder.tvName.setText(contact.fName+" "+contact.lName);
        holder.tvDate.setText(""+contact.hDay + " " + contact.hMonth);
        holder.imageView.setImageResource(R.drawable.birthday_image);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
    public void setFilter(List<Contact> contactModels) {
        contacts = new ArrayList<>();
        contacts.addAll(contactModels);
        notifyDataSetChanged();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView tvName,tvDate;
        public ContactViewHolder(View itemView) {
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.ivContactItem);
            tvName= (TextView) itemView.findViewById(R.id.tvContactItem);
            tvDate= (TextView) itemView.findViewById(R.id.tvDateContactItem);
        }
    }
}
