package com.example.vkfriendslist.adapters

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.vkfriendslist.R
import com.example.vkfriendslist.models.FriendModel
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class FriendsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mFriendsList: ArrayList<FriendModel> = ArrayList()

    fun setupFriends(friendList: ArrayList<FriendModel>) {
        mFriendsList.clear()
        mFriendsList.addAll(friendList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is FriendsViewHolder) {
            holder.bind(mFriendsList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.cell_friend, parent, false)
        return FriendsViewHolder(itemView = itemView)
    }

    override fun getItemCount(): Int {
        return mFriendsList.count()
    }

    class FriendsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private var mUserName: TextView = itemView.findViewById(R.id.friend_name)
        private var mFriendAvatar: CircleImageView = itemView.findViewById(R.id.friend_avatar)
        private var mFriendCity: TextView = itemView.findViewById(R.id.friend_city)
        private var mFriendOnline: View = itemView.findViewById(R.id.friend_online)

        fun bind(friendModel: FriendModel) {

            friendModel.avatar?.let {url -> Picasso.with(itemView.context).load(url).into(mFriendAvatar)}
            Picasso.with(itemView.context).load(friendModel.avatar)

            val userNameText = "${friendModel.name} ${friendModel.surname}"
            mUserName.text = userNameText
            mFriendCity.text = itemView.context.getString(R.string.friend_no_city)
            friendModel.city?.let{city -> mFriendCity.text = city}

            if(friendModel.isOnline){
                mFriendOnline.visibility = View.VISIBLE
            } else {
                mFriendOnline.visibility = View.GONE
            }
        }
    }
}