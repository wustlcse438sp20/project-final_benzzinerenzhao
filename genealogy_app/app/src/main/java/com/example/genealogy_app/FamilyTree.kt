package com.example.genealogy_app

import android.graphics.*
import android.graphics.drawable.Drawable
import com.example.genealogy_app.DataClasses.Member
import com.example.genealogy_app.DataClasses.Membership
import com.example.genealogy_app.DataClasses.Person
import com.example.genealogy_app.DataClasses.Spouse
import java.util.*

class FamilyTree(ancestor:Member) :Drawable(){
    
    

    val textHeight = 18.0f
    val viewUnit = 30.0f
    val signSize = viewUnit
    val viewMargin = viewUnit * 2
    val fontSize = viewUnit
    val memberWidth = viewUnit * 6
    val memberHeight = viewUnit * 9
    val horizontalDistance = viewUnit * 5
    val horizSmallDistance = viewUnit * 2.5f
    val verticalDistance = viewUnit * 5

    private val lineColor = -0x808081
    private val redColor = -0x10000
    private val blueColor = -0xffff01
    private val blackColor = -0x1000000

    val mAncestor: Member
    val mPaint:Paint

    init{
        mPaint = Paint()
        mPaint.setAntiAlias(true)
        mAncestor = ancestor
        layout()
    }
    
    
    override fun draw(canvas: Canvas) {
        // TODO Auto-generated method stub
        if (mAncestor != null) {
            drawFamily(canvas, mAncestor)
            if (!isSingle(mAncestor)) {
                drawLine(canvas, mAncestor)
            }
        }
    }
    
    
    private fun drawLine(canvas: Canvas, member: Member) {
        mPaint.color = lineColor
        mPaint.strokeWidth = 1f

        val spouses = member.spouses
        if (spouses != null) {
            var last: Membership = member
            for (spouse in spouses) {
                canvas.drawLine(
                    last.x + member.width,
                    last.y + last.height / 2,
                    spouse.x,
                    spouse.y + spouse.height / 2,
                    mPaint
                )
                last = spouse
            }
        }

        if (getChildCount(member) > 0) {
            val children = member.children
            if (children!!.size == 1) {
                val child = children[0]
                canvas.drawLine(
                    member.x + member.width / 2,
                    member.y + member.height + signSize,
                    child.x + child.width / 2,
                    child.y,
                    mPaint
                )
            } else {
                val middleY: Float =
                    member.y + member.height + verticalDistance / 2
                canvas.drawLine(
                    member.x + member.width / 2,
                    member.y + member.height + signSize,
                    member.x + member.width / 2,
                    middleY,
                    mPaint
                )
                val first = children[0]
                val last = children[children.size - 1]
                canvas.drawLine(
                    first.x + first.width / 2,
                    middleY,
                    last.x + last.width / 2,
                    middleY,
                    mPaint
                )
                for (child in children) canvas.drawLine(
                    child.x + child.width / 2,
                    middleY,
                    child.x + child.width / 2,
                    child.y,
                    mPaint
                )
            }
        }
    }


    fun drawMember(canvas: Canvas, member: Membership) {
        val person: Person = member.person
        if (person != null) {
            mPaint.textSize = fontSize
            val bounds = Rect()
            val name: String = getName(person)
            mPaint.strokeWidth = 1f
            mPaint.getTextBounds(name, 0, name.length, bounds)
            /*val photo: Bitmap = person.getPhoto()
            if (photo != null) {
                var x = member.x
                var y = member.y
                var height = member.height - bounds.height()
                var width = member.width
                
                val iRatio = photo.height.toFloat() / photo.width
                
                val rRatio = height / width
                
                if (iRatio < rRatio) {
                    val h = width * iRatio
                    y += (height - h) / 2
                    height = h
                } else {
                    val w = height / iRatio
                    x += (width - w) / 2
                    width = w
                }
                val dst = RectF(x, y, x + width, y + height)
                canvas.drawBitmap(photo, null, dst, mPaint)
            }*/
            
            val x = member.x + (member.width - bounds.width()) / 2
            val y = member.y + member.height - mPaint.fontMetrics.descent
            mPaint.color = blackColor
            mPaint.style = Paint.Style.FILL
            canvas.drawText(name, x, y, mPaint)
        }
        
        /*if (member === mSelectedMember) mPaint.color =
            FamilyTree.redColor else if (member === mBiologicalParent) mPaint.color =
            FamilyTree.blueColor else mPaint.color = FamilyTree.lineColor*/
        
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 2f
        canvas.drawRect(member.getBounds(), mPaint)
        if (member is Member) {
            val m = member
            if (!isSingle(m)) {
                mPaint.color = lineColor
                mPaint.strokeWidth = 1f
                mPaint.style = Paint.Style.STROKE
                val x = member.x + member.width / 2
                val y = member.y + member.height
                val half: Float = signSize / 2
                canvas.drawRect(x - half, y, x + half, y + signSize, mPaint)
                
                mPaint.color = blackColor
                mPaint.strokeWidth = 2f
                canvas.drawLine(x - half + 1, y + half, x + half - 1, y + half, mPaint)
                
                //if (!m.expanded) canvas.drawLine(x, y + 1, x, y + signSize - 1, mPaint)
            }
        }
    }


    private fun drawFamily(canvas: Canvas, member: Member) {
        drawMember(canvas, member)
        
        /*if (member === mSelectedMember && mBiologicalParent != null) {
            val x1: Float = mBiologicalParent.x + mBiologicalParent.width / 2
            val y1: Float = mBiologicalParent.y + mBiologicalParent.height
            
            val x2 = member.x + member.width / 2
            val y2 = member.y
            
            val y: Float = y2 - FamilyTree.verticalDistance / 2
            mPaint.color = FamilyTree.blueColor
            mPaint.strokeWidth = 2f
            canvas.drawLine(x1, y1, x1, y, mPaint)
            canvas.drawLine(x1, y, x2, y, mPaint)
            canvas.drawLine(x2, y, x2, y2, mPaint)
        }*/
        
        if (!isSingle(member)) {
            val spouses = member.spouses
            if (spouses != null) {
                for (spouse in spouses) drawMember(canvas, spouse)
            }
            if (getChildCount(member) > 0) {
                val children = member.children
                for (child in children!!) {
                    drawFamily(canvas, child)
                    if (!isSingle(member)) {
                        drawLine(canvas, child)
                    }
                }
            }
        }
    }
    

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
        return (mAncestor.fullHeight + viewMargin * 2 + 0.5f).toInt()
    }

    override fun getIntrinsicWidth(): Int { // TODO Auto-generated method stub
        return (mAncestor.fullWidth + viewMargin * 2 + 0.5f).toInt()
    }


    
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
            val children= member.children
            for (child in children!!) {
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
        val spouses = member.spouses
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
            val spouses = member.spouses
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
                val children = member.children
                for (child in children!!) {
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

    fun getChildCount(member: Member):Int{
        if (member.children!=null){
            return member.children!!.size
        }
        return 0
    }

    fun isSingle(member: Member):Boolean{
        if(member.children==null&&member.spouses==null){
            return true
        }else if(member.children==null){
            if(member.spouses!!.size>0){
                return false
            }
            return true
        }else if(member.spouses==null){
            if(member.children!!.size>0){
                return false
            }
            return true
        }
        if(member.children!!.size>0||member.spouses!!.size>0){
            return false
        }
        return true
    }

    fun getParent(member: Member): Member?{
        if (member.father != null && member.father is Member){
            return member.father as Member
        }else if (member.mother != null && member.mother is Member) {
            return member.mother as Member
        } else {
            return null
        }
    }

    fun getName(person: Person): String {
        if (person.surname == null) {
            if (person.givenName == null) {
                return ""
            }else {
                return person.givenName
            }
        } else if (person.givenName == null) {
            return person.surname
        } else {
            return person.givenName + " " + person.surname
        }
}


}