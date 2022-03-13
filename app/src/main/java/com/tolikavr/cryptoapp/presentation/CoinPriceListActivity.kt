package com.tolikavr.cryptoapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tolikavr.cryptoapp.R
import com.tolikavr.cryptoapp.data.network.model.CoinInfoDto
import com.tolikavr.cryptoapp.presentation.adapters.CoinInfoAdapter
import kotlinx.android.synthetic.main.activity_coin_prce_list.*

class CoinPriceListActivity : AppCompatActivity() {

  private lateinit var viewModel: CoinViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_coin_prce_list)
    val adapter = CoinInfoAdapter(this)
    adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
      override fun onCoinClick(coinPriceInfo: CoinInfoDto) {
        val intent = CoinDetailActivity.newIntent(
          this@CoinPriceListActivity,
          coinPriceInfo.fromSymbol
        )
        startActivity(intent)
      }
    }
    rvCoinPriceList.adapter = adapter
    viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
    viewModel.priceList.observe(this, Observer {
      adapter.coinInfoList = it
    })
  }
}
