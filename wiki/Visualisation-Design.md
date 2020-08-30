#Visualisation Design

The key user need for the visualisation component of the program, as discerned from the brief, were the need for 'live visual feedback about the search'. 
The need for live updating of the visualisation, and the need for presented information to be meaningful and an accurate reflection of the search, were identified as key requirements.

Initially, a brainstorm was conducted to generate ideas for the visualisation, and these ideas were narrowed down through a group discussion session. We decided on 3 graphical components: a chart of CPU usage by the application, a chart of memory usage by the application and a chart displaying the best schedule found graphically. We also decided to include various text based elements such as elapsed time. These components were thought to provide a good reflection of the status of the search process.

From these decided upon features, an early design mockup was created, which can be seen below.

![Current Visualisation](Vis_mockup.png)

This design was largely kept - more text elements were added, and the colour scheme was changed due to grey providing better contrast. The theme button was also removed due to being unneccessary and replaced by an exit button.

To ensure live updating of the visualisation, the controller calls methods in the algorithm module to receive information about current algorithm status. A polling method was used to update the visualisation regularly until algorithm completion. 