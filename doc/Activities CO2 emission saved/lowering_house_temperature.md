CO2 emission saved for lowering house temperature
==================

Since there's not yet been fully decided which implementation we're gonna use I'm not yet fully able to make a formula for this part. I have however done some 
research in advance to hopefully be able to do the rest quicky once we do decide.

Multiple sources state that by turning the temperature down by one degree, your CO2 emission per year will go down by 6%. Since this is exponential the formula will
contain a part like x - (x * 0,94^(21 - temperature)). This x will be the amount of CO2 emission an average house emits in a day.



Since those amount differ greatly depending on what kind of house you have there was a site that gave a useful graph that show the amounts of gas usage depending 
on what kind of type house you have. Those types are:
1. apartment (1000 m^3)
2. rowhouse (1350 m^3)
3. corner house (1580 m^3)
4. 2 under 1 roof house (1800 m^3)
5. single house (2410 m^3)

The data on Nefit states that about 73% of gas a house uses in a year is used for heating and on wikipedia is stated that using 1 m^3 of gas emits about 1,8 kg CO2 
so these numbers become:
1. apartment (1314 kg CO2)
2. rowhouse (1773,9 kg CO2)
3. corner house (2076,12 kg CO2)
4. 2 under 1 roof (2365,2 kg CO2)
5. single house (3166,74 kg CO2)

Data given by the KNMI says that, on average, a year has 85 days above 20 degrees where the heating would be off. this means that an average 
year has 280 days where the heating would be on and energy can be saved. This gives the house type a daily emission of:
1. apartment (4,693 kg CO2)
2. rowhouse (6,335 kg CO2)
3. corner house (7,415 kg CO2)
4. 2 under 1 roof (8,447 kg CO2)
5. single house (11,310 kg CO2)

## Final result
The final calculation will require the user to input 2 parameters. these are house type (int from 1 to 5) and temperature 
(int: tenth of a degree celsius so 18 degrees celsius is 180).
If we fill these results in into the first formula we get:
1. apartment = 4,693 - (4,693 * 0,94^(21 - temperature))
2. rowhouse = 6,335 - (6,335 * 0,94^(21 - temperature))
3. corner house = 7,415 - (7,415 * 0,94^(21 - temperature))
4. 2 under 1 roof = 8,447 - (8,447 * 0,94^(21 - temperature))
5. single house = 11,310 - (11,310 * 0,94^(21 - temperature))

## Documentation
1. [link 1 that states 6% saved for lowering temperature](https://www.essent.nl/content/particulier/essenties/groen-doen/groene-voetafdruk-1-verwarming-graadje-lager.html#)
2. [link 2 that states 6% saved for lowering temperature](https://www.nporadio1.nl/homepage/7995-warme-truiendag-verwarming-lager-bespaar-co2)
3. [around 73% of gas is used for heating](https://www.nefit.nl/consument/over_nefit/nieuws/12_weetjes_over_verwarming_en_warm_water)
4. [amount of gas used, on average, per house type](https://www.milieucentraal.nl/energie-besparen/snel-besparen/grip-op-je-energierekening/gemiddeld-energieverbruik/)
5. [amount of emission by burning gas](https://nl.wikipedia.org/wiki/Aardgas#CO2-uitstoot)
6. [amount of warm days a year where the heating would be off](https://www.knmi.nl/kennis-en-datacentrum/uitleg/warme-dagen)