package com.example.superioraluminiumglass

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class sectionadapter(val ctx:Context, val layoutid:Int, val slist:List<section>)
    :ArrayAdapter<section>(ctx,layoutid,slist) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val l = LayoutInflater.from(ctx)
        val v=l.inflate(layoutid,null)
       var name=v.findViewById<TextView>(R.id.sectionname)
        var inchessize=v.findViewById<TextView>(R.id.sizeininch)
        var feetsize=v.findViewById<TextView>(R.id.sizeinfeet)
        var lengthsize=v.findViewById<TextView>(R.id.Lengthsize)
        var perfeetprice=v.findViewById<TextView>(R.id.perfootprice)
        var totalprice=v.findViewById<TextView>(R.id.TotalPrice)
        var lengthused=v.findViewById<TextView>(R.id.lengthused)

        val s=slist[position]
        name.text=s.name
        inchessize.text=s.inches.toString()
        feetsize.text="%.3f".format(s.feet)
        lengthsize.text=s.lengthsize.toString()
        perfeetprice.text=s.perfeetprice.toString()
        totalprice.text="%.2f".format(s.totalprice)
        lengthused.text="%.3f".format(s.lengthused)
        return v
    }

}