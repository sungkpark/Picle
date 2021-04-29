CO2 emission saved for buying local produce
==================

Once again, since we're only using one parameter (amount of products) the resulting formula will just be constant times amount of products.
At first I tried to calculate an average myself by researching multiple source. But since there are just too many variables that all differ way too much per case
(distance, weight of the product, amount of products per shipment, type of transport) this was just impossible. Luckily there was a source that 
just straight up gave us an answer. This source gives the amount of CO2 emission cause by transporting the products for a simple meal.
It states that a simple meal causes at minimum 0,08kg CO2 and at maximum 2,71 kg CO2. Since a simple meal uses about 4 products we'll divide these number by 4 to get
0,02 kg CO3 at mminimum and 0,6775 kg CO2 at maximum per product. Taking the average of this to get to the emission for an average product gives us 0,34875 kg CO2.
Finally we just need to subtract 0,02 from 0,34875 because that's emission we can't avoid and that gives us a constant of 0,32875 kg CO2 per product.

So the final formula will be 0,32875 * amount of products.


## Documentation
1. [For this activity I only ended up using one source since it was based on clear and official research](http://sustainablefootprint.org/nl/how-many-kilometers-does-your-plate-contain/)

