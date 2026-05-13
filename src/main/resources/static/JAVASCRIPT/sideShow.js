/*
   Filename: sideShow.js
   Author:   Andre Long
   Date:     10/13/2024
   Description: Smooth slideshow for banner images
*/
console.log("LIVE sideShow.js is running");

window.onload = function() {
      setInterval(start, 3000);
};

let i = 0;

function start() {


      const imagePaths = [
            "../IMAGES/dilbert1.jpg",
            "../IMAGES/dilbert2.jpg",
            "../IMAGES/dilbert3.jpg",
            "../IMAGES/dilbert4.jpg",
            "../IMAGES/dilbert5.jpg",
            "../IMAGES/dilbert6.jpg",
            "../IMAGES/dilbert7.jpg",
            "../IMAGES/dilbert8.jpg",
            "../IMAGES/dilbert9.jpg",
            "../IMAGES/dilbert10.jpg",
            "../IMAGES/dilbert11.jpg",
            "../IMAGES/dilbert12.jpg"
      ];

      const description = [
            "Motivation",
            "Career Success",
            "Catbert Advise",
            "Make boss look good",
            "Not my job",
            "The one thing",
            "The block chain",
            "Management feedback",
            "Risk Management",
            "Lay Offs",
            "The Dynamic",
            "Change Management"
      ];

      const img = document.getElementById("bannerImage");

      img.src = imagePaths[i];
      img.alt = description[i];
      img.title = description[i];

      // FORCE BROWSER TO RELOAD IMAGE
      img.src = imagePaths[i] + "?v=" + new Date().getTime();
      img.alt = description[i];
      img.title = description[i];

      i++;
      if (i >= imagePaths.length) {
            i = 0;
      }
}
