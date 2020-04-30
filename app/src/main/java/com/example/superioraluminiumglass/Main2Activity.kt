package com.example.superioraluminiumglass

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_view_items.*
import kotlinx.android.synthetic.main.section_detail_alter_box.*
import java.util.*

class Main2Activity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {

    override fun onNothingSelected(p0: AdapterView<*>?) {


    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

        if(sectionlist[p2].isNotEmpty()) {
            var sectionname = sectionlist[p2]
            val builder = android.app.AlertDialog.Builder(this)
            builder.setTitle("Detail")
            builder.setCancelable(false)

            val l = LayoutInflater.from(this)
            val v = l.inflate(R.layout.section_detail_alter_box, null)
            builder.setView(v)
            var secname = v.findViewById<TextView>(R.id.sectionnameinbox)
            var inches = v.findViewById<EditText>(R.id.sizeininchinbox)
            var lengthsize = v.findViewById<EditText>(R.id.Lengthsizeinbox)
            var perfeetprice = v.findViewById<EditText>(R.id.perfootpriceinbox)
            secname.text = sectionname



            builder.setPositiveButton("OK", object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {}
            })
            builder.setNegativeButton("Cancel", object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {}
            })


            val a = builder.create()
            a.show()
            a.setCanceledOnTouchOutside(false)
            var bp :Button=a.getButton(android.app.AlertDialog.BUTTON_POSITIVE)
            var bn:Button=a.getButton(android.app.AlertDialog.BUTTON_NEGATIVE)
            bp.setOnClickListener {

            if (inches.text.toString().isEmpty())
            {
                inches.setError("Please Enter Size In inches")
                inches.requestFocus()
            //    return@setOnClickListener
            }
            else if (lengthsize.text.toString().isEmpty())
            {
                lengthsize.setError("Please Enter Length Size")
                lengthsize.requestFocus()
            }
            else if  (perfeetprice.text.toString().isEmpty()) {
                perfeetprice.setError("Please Per feet Price")
                perfeetprice.requestFocus()

            }else
            {   ///converting string to int(ignoring +) for calculate total inches
                var str=inches.text.toString().trim()
                val arr = str.split("++++","+++","++","+").toTypedArray()
                val length=arr.count()-1
                var inch:Int=0

                for(i in 0..length)
                {
                    var t=arr[i].toInt()
                    inch= inch+t
                }
                ///////////////////////////////

                var totalprice:Double
                var lengthused:Double
                var inche=inch.toDouble()
                var feet=inche/12.0
                if (feet in 0.0..0.9999999999999999)
                {
                    totalprice=perfeetprice.text.toString().toDouble()
                    lengthused=0.1
                    totalbill=totalbill+totalprice

                }else {
                    totalprice = feet * perfeetprice.text.toString().toDouble()
                    lengthused = feet / lengthsize.text.toString().toDouble()
                    totalbill=totalbill+totalprice
                }
                var sections=section(sectionname,inche,feet,perfeetprice.text.toString().toInt(),lengthsize.text.toString().toInt(),lengthused,totalprice )

                sectiondatalist.add(sections)
                val adap=sectionadapter(this@Main2Activity,R.layout.list_view_items,sectiondatalist)
                rview.adapter=adap
                billview.text="%.2f".format(totalbill)
                if(discountpercent!=0)
                {
                    discounttextedit.setText(discountpercent.toString())
                    labourcost.setText(labourcostvalue.toString())
                }
                else
                {
                    discountbillview.text="%.2f".format(totalbill)
                }
                Toast.makeText(this,"Added",Toast.LENGTH_SHORT).show()
                a.dismiss()
            }

        }
             bn.setOnClickListener {
                 a.dismiss()

             }

        }
        else
        {

        }

    }


    lateinit var sectionlist:Array<String>
    lateinit var spiner:Spinner
    lateinit var rview:ListView
    lateinit var billview:TextView
    lateinit var discountbillview:TextView
    lateinit var discounttextedit:EditText
    lateinit var labourcost:EditText
    lateinit var sectiondatalist: MutableList<section>
    lateinit var customernaame:EditText


    var totalbill:Double=0.0
    var discountedbill:Double=0.0
    var discountpercent:Int=0
    var labourcostvalue:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        spiner= findViewById(R.id.Sectionspinner)
        rview=findViewById(R.id.rview)
        discounttextedit=findViewById(R.id.discount)
        labourcost=findViewById(R.id.labour)
        billview=findViewById(R.id.tottalbillview)
        discountbillview=findViewById(R.id.disccountbill)
        customernaame=findViewById<EditText>(R.id.nametextbox)
        billview.text="0"
        discountbillview.text="0"
        rview.setLongClickable(true)
        spiner.setOnItemSelectedListener(this)
        sectiondatalist= mutableListOf()
        fillsectionspinner()
        setSupportActionBar(toolbar)
    rview.setOnItemLongClickListener { adapterView, view, i, l ->


        var currentitem= sectiondatalist[i]
        var sectionname = currentitem.name
        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Update : ")
        builder.setCancelable(false)

        val l = LayoutInflater.from(this)
        val v = l.inflate(R.layout.section_detail_alter_box, null)
        builder.setView(v)
        var secname = v.findViewById<TextView>(R.id.sectionnameinbox)
        var inches = v.findViewById<EditText>(R.id.sizeininchinbox)
        var lengthsize = v.findViewById<EditText>(R.id.Lengthsizeinbox)
        var perfeetprice = v.findViewById<EditText>(R.id.perfootpriceinbox)
        secname.text = sectionname
        inches.setText(currentitem.inches.toInt().toString())
        lengthsize.setText(currentitem.lengthsize.toString())
        perfeetprice.setText(currentitem.perfeetprice.toString())


        builder.setPositiveButton("update", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {}
        })
        builder.setNegativeButton("Cancel", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {}
        })


        val a = builder.create()
        a.show()
        a.setCanceledOnTouchOutside(false)
        var bp :Button=a!!.getButton(android.app.AlertDialog.BUTTON_POSITIVE)
        var bn:Button=a!!.getButton(android.app.AlertDialog.BUTTON_NEGATIVE)
        bp.setOnClickListener {

            if (inches.text.toString().isEmpty())
            {
                inches.setError("Please Enter Size In inches")
                inches.requestFocus()
                //    return@setOnClickListener
            }
            else if (lengthsize.text.toString().isEmpty())
            {
                lengthsize.setError("Please Enter Length Size")
                lengthsize.requestFocus()
            }
            else if  (perfeetprice.text.toString().isEmpty()) {
                perfeetprice.setError("Please Per feet Price")
                perfeetprice.requestFocus()

            }else
            {
                ///converting string to int(ignoring +) for calculate total inches
                var str=inches.text.toString().trim()
                val arr = str.split("++++","+++","++","+").toTypedArray()
                val length=arr.count()-1
                var inch:Int=0

                for(i in 0..length)
                {
                    var t=arr[i].toInt()
                    inch= inch+t
                }
                ///////////////////////////////

                totalbill=totalbill-currentitem.totalprice
                var totalprice:Double
                var lengthused:Double
                var inche=inch.toDouble()
                var feet=inche/12.0
                if (feet in 0.0..0.9999999999999999)
                {
                    totalprice=perfeetprice.text.toString().toDouble()
                    lengthused=0.1
                    totalbill=totalbill+totalprice

                }else {
                    totalprice = feet * perfeetprice.text.toString().toDouble()
                    lengthused = feet / lengthsize.text.toString().toDouble()
                    totalbill=totalbill+totalprice
                }
                var sections=section(sectionname,inche,feet,perfeetprice.text.toString().toInt(),lengthsize.text.toString().toInt(),lengthused,totalprice )
                sectiondatalist.removeAt(i)
                sectiondatalist.add(i,sections)
                val adap=sectionadapter(this@Main2Activity,R.layout.list_view_items,sectiondatalist)
                rview.adapter=adap
                billview.text="%.2f".format(totalbill)
                if(discountpercent!=0)
                {
                    discounttextedit.setText(discountpercent.toString())
                }
                else
                {
                    discountbillview.text="%.2f".format(totalbill)
                }
                Toast.makeText(this,"Updated",Toast.LENGTH_SHORT).show()
                a.dismiss()
            }

        }
        bn.setOnClickListener {
            a.dismiss()

        }


        false
        }
    rview.setOnItemClickListener { adapterView, view, i, l ->

        Toast.makeText(this,"Long press to Edit",Toast.LENGTH_SHORT).show()
    }


        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            if(customernaame.text.isNotEmpty())
            {
                var a: String = "Customer Name: ${customernaame.text.toString()}\n\nQutation Detail:\n\n"
               var b:String=""
                var num=1
                for( i in sectiondatalist)
               {
                   b=b+"$num): Section:${i.name.toString()}   Size(Inches):${"%.2f".format(i.inches)}   Size(Feets):${"%.2f".format(i.feet)}   Per feet Price:${i.perfeetprice.toString()}  Total:${"%.2f".format(i.totalprice)}\n"
                   num++
               }

               var c="\nLabour Cost:${labourcost.text} \nTotal Amount: ${"%.0f".format(totalbill)}\nDiscount :${discountpercent}% \nDiscounted Price : ${"%.0f".format(discountedbill)}\n\n\nSuperior Aluminium & Glass \nCEO & Founder : Hafiz Ibtsaam Ali\nContact No: 0334-6565255 \nEmail:ibtsaamali255@yahoo.com \n\nShared by  Superior App \nDeveloped By Hannan Shahid(03341617255)"
                var detail ="$a$b$c"
                  var d=detail
                var i = Intent()
                i.action = Intent.ACTION_SEND
                i.putExtra(Intent.EXTRA_TEXT, d)
                i.setType("text/plain")
                startActivity(Intent.createChooser(i, "Share To: "))
                Snackbar.make(view, "Qutation Sharing...... ", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
            else
            {
                Toast.makeText(this,"Enter Customer Name ",Toast.LENGTH_LONG).show()
            customernaame.requestFocus()
            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)
        discounttextedit.addTextChangedListener(object : TextWatcher
        {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int)
            {
                if(totalbill!=0.0 && p0.toString().length!=0 && p0.toString().toInt()<=100)
                {
                    var discount = totalbill.toDouble() * p0.toString().toDouble()
                    var divid = discount / 100.0
                    discountedbill = totalbill - divid
                    discountbillview.text = "%.2f".format(discountedbill)
                    discountpercent=p0.toString().toInt()
                }else
                {
                    discountpercent=0
                    discountbillview.text=billview?.text.toString()
                }
                }

        })

        labourcost.addTextChangedListener(object :TextWatcher
        {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int)
            {
                try{
                    if (totalbill != 0.0 && p0.toString().length != 0)
                    {
                        totalbill=totalbill-labourcostvalue
                    totalbill = totalbill + p0.toString().toInt()
                    billview.text = "%.2f".format(totalbill)
                    labourcostvalue = p0.toString().toInt()
                        discountbillview.text=billview?.text.toString()
                    if (discountpercent != 0) {
                        discounttextedit.setText(discountpercent.toString())
                    } else {
                    }
                } else {
                    totalbill = totalbill - labourcostvalue
                    billview.text = "%.2f".format(totalbill)
                        discountbillview.text=billview?.text.toString()
                    labourcostvalue = 0
                    if (discountpercent != 0) {
                        discounttextedit.setText(discountpercent.toString())
                    } else {
                    }
                }
                }
                catch (e:Exception)
                {

                }
            }
        })
    }

    private fun fillsectionspinner()
    {
        sectionlist= arrayOf("","DC26c","Dc30c","M24","M28","M23","D29","D41","D52","D52a","D54","D48","D46","D51","D59")
        val adapter=ArrayAdapter(this,android.R.layout.simple_spinner_item,sectionlist)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spiner.adapter=adapter

    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main2, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return false



    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                rview.adapter = null
                 totalbill=0.0
                 discountedbill=0.0
                 discountpercent=0
                billview.text="0"
                discountbillview.text="0"
                sectiondatalist.clear()
                discounttextedit.setText("")
            }
            R.id.nav_gallery -> {
                startActivity(Intent(this,aboutus::class.java))
            }

            R.id.nav_tools -> {
                startActivity(Intent(this,aboutus::class.java))
            }
            R.id.nav_share -> {
                var d="Superior Aluminium & Glass\nVersion 1.2\n\nDeveloped by Hannan Shahid\nContact:03341617255\nEmail:hannanshahid0@gmail.com"
                var i = Intent()
                i.action = Intent.ACTION_SEND
                i.putExtra(Intent.EXTRA_TEXT, d)
                i.setType("text/plain")
                startActivity(Intent.createChooser(i, "Share To: "))
            }

        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
