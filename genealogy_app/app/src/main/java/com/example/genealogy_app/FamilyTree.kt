package com.example.genealogy_app

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import com.example.genealogy_app.DataClasses.Member
import com.example.genealogy_app.DataClasses.Spouse
import java.util.*

class FamilyTree :Drawable(){
    private val mPaint: Paint? = null
    override fun draw(canvas: Canvas) {
    }
    val textHeight = 18.0f
    val viewUnit = 15.0f
    val signSize = viewUnit
    val viewMargin = viewUnit * 2
    val fontSize = viewUnit
    val memberWidth = viewUnit * 6
    val memberHeight = viewUnit * 9
    val horizontalDistance = viewUnit * 5
    val horizSmallDistance = viewUnit * 2.5f
    val verticalDistance = viewUnit * 5

    override fun setAlpha(alpha: Int) { // TODO Auto-generated method stub
        mPaint!!.alpha = alpha
    }

    override fun setColorFilter(cf: ColorFilter?) { // TODO Auto-generated method stub
        mPaint!!.colorFilter = cf
    }

    override fun getOpacity(): Int { // TODO Auto-generated method stub
        return PixelFormat.TRANSLUCENT
    }

    override fun getIntrinsicHeight(): Int { // TODO Auto-generated method stub
        return (mAncestor.fullHeight + viewMargin * 2 + 0.5f) as Int
    }

    override fun getIntrinsicWidth(): Int { // TODO Auto-generated method stub
        return (mAncestor.fullWidth + viewMargin * 2 + 0.5f) as Int
    }


    lateinit var mAncestor: Member
    fun layout() {
        if (mAncestor != null) {
            calcDistance(mAncestor, 0)
            locate(
                mAncestor,
                viewMargin,
                viewMargin
            )
        }
    }
    private fun calcDistance(member: Member, depth: Int) {
        member.depth = depth
        member.allChildrenAreSingle = true
        member.width = memberWidth
        member.height = memberHeight
        member.fullWidth = memberWidth
        member.fullHeight = memberHeight
        var maxWidth: Float = memberWidth
        if (getChildCount(member)> 0) {
            val children: ArrayList<Member> = member.children
            for (child in children) {
                calcDistance(child, depth + 1)
                if (!isSingle(child)){
                    member.allChildrenAreSingle = false
                }
            }
            val gap: Float = if (member.allChildrenAreSingle) horizSmallDistance else horizontalDistance
            maxWidth = -gap
            var maxHeight = 0.0f
            for (child in children) {
                maxWidth += child.fullWidth + gap
                if (child.fullHeight > maxHeight) maxHeight = child.fullHeight
            }
            member.fullHeight =
                maxHeight + member.height + verticalDistance
        }
        val spouses: ArrayList<Spouse> = member.spouses
        if (spouses != null && spouses.size > 0) {
            for (spouse in spouses) {
                spouse.width = memberWidth
                spouse.height = memberHeight
            }
            val width: Float = (horizSmallDistance + memberWidth) * spouses.size + memberWidth
            if (width > maxWidth) {
                maxWidth = width
            }
        }
        member.fullWidth = maxWidth
    }

    private fun locate(member: Member, left: Float, top: Float) {
        val y: Float = top + member.height + verticalDistance
        member.y = top
        member.x = left + (member.fullWidth - member.width) / 2
        var parent: Member? = getParent(member)
        if (parent != null) {
            if (getChildCount(parent) === 1) {
                member.x = parent.x
            }
        }
        if (!isSingle(member)) {
            val spouses: ArrayList<Spouse> = member.spouses
            if (spouses != null) {
                val mWidth: Float = (horizSmallDistance + memberWidth) * spouses.size + memberWidth
                val right = member.x + mWidth
                if (right > left + member.fullWidth) {
                    member.x = left + member.fullWidth - mWidth
                    while (parent != null && getChildCount(parent) === 1) {
                        parent.x = member.x
                        parent = getParent(parent)
                    }
                }
                var x: Float = member.x + horizSmallDistance + memberWidth
                for (spouse in spouses) {
                    spouse.x = x
                    spouse.y = top
                    x += horizSmallDistance + memberWidth
                }
            }
            var x = left
            val gap: Float = if (member.allChildrenAreSingle) horizSmallDistance else horizontalDistance
            val count: Int = getChildCount(member)
            if (count > 0) {
                val children: ArrayList<Member> = member.children
                for (child in children) {
                    locate(child, x, y)
                    x += child.fullWidth + gap
                }
                val last = children[count - 1]
                if (count > 1 && last.x < member.x) {
                    member.x = last.x
                }
            }
        }
    }

    private fun getChildCount(member: Member):Int{
        if (member.children!=null){
            return member.children.size
        }
        return 0
    }

    private fun isSingle(member: Member):Boolean{
        return ((member.children == null || member.children.size == 0) && (member.spouses == null || member.spouses.size == 0))
    }

    private fun getParent(member: Member): Member?{
        if (member.father != null && member.father is Member){
            return member.father as Member
        }else if (member.mother != null && member.mother is Member) {
            return member.mother as Member
        } else {
            return null
        }
    }
}