package com.tolikavr.cryptoapp.data.mapper

import com.google.gson.Gson
import com.tolikavr.cryptoapp.data.database.CoinInfoDbModel
import com.tolikavr.cryptoapp.data.network.model.CoinInfoDto
import com.tolikavr.cryptoapp.data.network.model.CoinInfoJsonContainerDto
import com.tolikavr.cryptoapp.data.network.model.CoinNameListDto
import com.tolikavr.cryptoapp.domain.CoinInfo

class CoinMapper {

  fun mapDtoToDbModel(dto: CoinInfoDto) = CoinInfoDbModel(
    fromSymbol = dto.fromSymbol,
    toSymbol = dto.toSymbol,
    price = dto.price,
    lastUpdate = dto.lastUpdate,
    highDay = dto.highDay,
    lowDay = dto.lowDay,
    lastMarket = dto.lastMarket,
    imageUrl = dto.imageUrl,
  )

  fun mapJsonContainerToListCoinInfo(jsonContainer: CoinInfoJsonContainerDto): List<CoinInfoDto> {
    val result = mutableListOf<CoinInfoDto>()
    val jsonObject = jsonContainer.json ?: return result
    val coinKeySet = jsonObject.keySet()
    for (coinKey in coinKeySet) {
      val currencyJson = jsonObject.getAsJsonObject(coinKey)
      val currencyKeySet = currencyJson.keySet()
      for (currencyKey in currencyKeySet) {
        val priceInfo = Gson().fromJson(
          currencyJson.getAsJsonObject(currencyKey),
          CoinInfoDto::class.java
        )
        result.add(priceInfo)
      }
    }
    return result
  }

  fun mapNamesListToString(namesListDto: CoinNameListDto): String {
    return namesListDto.names?.map { it.coinName?.name }?.joinToString(",") ?: ""
  }

  fun mapDbModelToEntity(dbModel: CoinInfoDbModel): CoinInfo {
    return CoinInfo(
      fromSymbol = dbModel.fromSymbol,
      toSymbol = dbModel.toSymbol,
      price = dbModel.price,
      lastUpdate = dbModel.lastUpdate,
      highDay = dbModel.highDay,
      lowDay = dbModel.lowDay,
      lastMarket = dbModel.lastMarket,
      imageUrl = dbModel.imageUrl,
    )
  }

}