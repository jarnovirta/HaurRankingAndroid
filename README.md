# HaurRankingAndroid 

This is an Android application for a shooting club's (Haukilahden urheiluampujat ry, www.haur.fi) internal ranking based on [IPSC](http://www.ipsc.org/) classifiers scored using [PractiScore](https://practiscore.com/).

Results from [IPSC classifier stages](http://www.ipsc.org/classification/icsStages.php) scored using PractiScore can be imported into the application database and included in the ranking. The application reads the exported result data from PractiScore's match export file (.psc) and saves the selected results into its own database (an embedded SQLite database). 

Rankings are generated by IPSC Division. The application generates a PDF file of the current ranking using the [iText](https://itextpdf.com/en) library (under AGPL license).

## Screenshots 

Screenshots of the application are included in this project, in the [screenshots directory](https://github.com/jarnovirta/HaurRankingAndroid/tree/master/screenshots).
