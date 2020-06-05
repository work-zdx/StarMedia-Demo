package com.star.media

import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_content.*

class TestContentActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)

        btn_test_content.setOnClickListener {
            showLoading()
            val params = HashMap<Long, List<Int>>()
            params[0] = arrayListOf(1001, 1002, 1003, 1004, 1005, 1006, 1007, 1008, 1009)
            params[1] = arrayListOf(1011, 1012, 1013, 1014, 1015, 1016, 1017, 1018, 1019)
            params[2] = arrayListOf(1033, 1034, 1036, 1037, 1039, 1040, 1041, 1042)

            cv_content_result.request(params) {
                hideLoading()

                if (!it) {
                    Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}