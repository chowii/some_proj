package com.soho.sohoapp.customviews

import android.content.Context
import android.support.v7.content.res.AppCompatResources
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.soho.sohoapp.R
import com.soho.sohoapp.data.models.PropertyUser
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by Jovan on 17/09/2017.
 */

class UserAvatarView(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    @BindView(R.id.image_user_avatar)
    lateinit var userAvatarImageView: CircleImageView

    @BindView(R.id.txt_user_name)
    lateinit var userNameTextView: TextView

    @BindView(R.id.txt_user_role)
    lateinit var userRoleTextView: TextView


    init {
        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.view_user_avatar, this, true)
        ButterKnife.bind(this)
        configureAttributes(context, attrs)
    }

    private fun configureAttributes(context: Context, attrs: AttributeSet?) {
        val attributes = context.obtainStyledAttributes(attrs,
                R.styleable.UserAvatarView)
        val name = attributes.getString(R.styleable.UserAvatarView_name)
        val role = attributes.getString(R.styleable.UserAvatarView_name)
        val userNameTextViewVisibility = attributes.getInt(R.styleable.UserAvatarView_name_visibility, 0)
        val userRoleTextViewVisibility = attributes.getInt(R.styleable.UserAvatarView_role_visibility, 0)
        userNameTextView.text = name ?: ""
        userRoleTextView.text = role ?: ""
        userNameTextView.visibility = userNameTextViewVisibility
        userRoleTextView.visibility = userRoleTextViewVisibility
        attributes.recycle()
    }

    fun populateWithPropertyUser(propertyUser: PropertyUser?) {
        userNameTextView.text = propertyUser?.representingUserFullName() ?: ""
        userRoleTextView.text = propertyUser?.role?.capitalize() ?: ""
        propertyUser?.userDetails?.avatar?.smallImageUrl?.let { avatarStringUrl ->
            if (!avatarStringUrl.isEmpty()) {
                Picasso.with(userAvatarImageView.context)
                        .load(avatarStringUrl)
                        .placeholder(AppCompatResources.getDrawable(userAvatarImageView.context, R.drawable.ic_account_circle_black_48dp))
                        .error(AppCompatResources.getDrawable(userAvatarImageView.context, R.drawable.ic_account_circle_black_48dp))
                        .into(userAvatarImageView)
            } else {
                setPlaceholderAvatar()
            }
            return
        }
        //Default to placeholder if return not called
        setPlaceholderAvatar()
    }

    private fun setPlaceholderAvatar() {
        userAvatarImageView.setImageDrawable(AppCompatResources.getDrawable(userAvatarImageView.context, R.drawable.ic_account_circle_black_48dp))
    }

    fun setNameVisibility(visibility: Int) {
        userNameTextView.visibility = visibility
    }

    fun setRoleVisibility(visibility: Int) {
        userRoleTextView.visibility = visibility
    }
}
