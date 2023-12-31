package com.example.finproject

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.finproject.model.Produk
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "product.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "product_table"
        const val COL_ID = "ID"
        const val COL_NAME = "NAME"
        const val COL_IMAGE = "IMAGE"
        const val COL_DETAIL = "DETAIL"
        const val CREATE_TABLE_QUERY = "CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_NAME TEXT, $COL_IMAGE BLOB, $COL_DETAIL TEXT)"
        const val DROP_TABLE_QUERY = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DROP_TABLE_QUERY)
        onCreate(db)
    }

    fun insertProduct(product: Produk): Boolean {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_ID, product.id)
        cv.put(COL_NAME, product.name)
        cv.put(COL_IMAGE, bitmapToByteArray(product.image))
        cv.put(COL_DETAIL, product.detail)
        val result = db.insert(TABLE_NAME, null, cv)
        db.close()
        return result != -1L
    }

    @SuppressLint("Range")
    fun getAllProducts(): List<Produk> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        val products = mutableListOf<Produk>()
        val  cursorCount = cursor.count
        if(cursorCount>0){
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(cursor.getColumnIndex(COL_ID))
                    val name = cursor.getString(cursor.getColumnIndex(COL_NAME))
                    val image = cursor.getBlob(cursor.getColumnIndex(COL_IMAGE))
                    val detail = cursor.getString(cursor.getColumnIndex(COL_DETAIL))
                    val product = Produk(id, name, BitmapFactory.decodeByteArray(image, 0, image.size), detail)
                    products.add(product)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return products
    }

    fun deleteProduct(product: Produk): Boolean {
        val db = this.writableDatabase
        val selection = "$COL_NAME = ?"
        val selectionArgs = arrayOf(product.name)
        val result = db.delete(TABLE_NAME, selection, selectionArgs)
        db.close()
        return result > 0
    }

    fun updateProduct(product: Produk): Boolean {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_ID, product.id)
        cv.put(COL_NAME, product.name)
        cv.put(COL_IMAGE, bitmapToByteArray(product.image))
        cv.put(COL_DETAIL, product.detail)
        val selection = "$COL_NAME = ?"
        val selectionArgs = arrayOf(product.name)
        val result = db.update(TABLE_NAME, cv, selection, selectionArgs)
        db.close()
        return result > 0
    }

    private fun bitmapToByteArray(bitmap: Bitmap?): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

    fun insertData(name: String, image: ByteArray, detail: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_NAME, name)
        contentValues.put(COL_IMAGE, image)
        contentValues.put(COL_DETAIL, detail)
        val result = db.insert(TABLE_NAME, null, contentValues)
        return result != -1L
    }

    fun updateData(id: String, name: String, image: ByteArray, detail: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_ID, id)
        contentValues.put(COL_NAME, name)
        contentValues.put(COL_IMAGE, image)
        contentValues.put(COL_DETAIL, detail)
        val result = db.update(TABLE_NAME, contentValues, "$COL_ID = ?", arrayOf(id))
        return result > 0
    }

    fun deleteData(id: String): Boolean {
        val db = this.writableDatabase
        val result = db.delete(TABLE_NAME, "$COL_ID = ?", arrayOf(id))
        return result > 0
    }

    @SuppressLint("Range")
    fun getAllData():ArrayList<Produk>{
        val listModel = ArrayList<Produk>()
        val db = this.readableDatabase
        var cursor:Cursor?=null
        try {
            cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME,null)
        }catch (se: SQLiteException){
            db.execSQL(CREATE_TABLE_QUERY)
            return ArrayList()
        }

        var id:Int
        var name:String
        var imageArray:ByteArray
        var imageBmp:Bitmap
        var detail:String


        if(cursor.moveToFirst()) {
            do {
                //get data text
                id = cursor.getInt(cursor.getColumnIndex(COL_ID))
                name = cursor.getString(cursor.getColumnIndex(COL_NAME))
                detail = cursor.getString(cursor.getColumnIndex(COL_DETAIL))
                //get data image
                imageArray = cursor.getBlob(cursor.getColumnIndex(COL_IMAGE))
                //convert ByteArray to Bitmap
                val byteInputStream = ByteArrayInputStream(imageArray)
                imageBmp = BitmapFactory.decodeStream(byteInputStream)
                val model = Produk(id = Int, name = name, image = imageBmp,  detail = detail)
                listModel.add(model)
            } while (cursor.moveToNext())
        }
        return listModel
    }

}