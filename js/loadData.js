
//load the user and movie information as global
//movie info: movieID, title, year, genre0, genre1,...
//use info: userID,sex,age,occupation,state
var userInfo = [];
var movieInfo = [];

//mapping from movieID to index of movieInfo
//mapping from userID to index of userInfo
var movieID2Idx = [];
var userID2Idx = [];


var loadData = function (){
    d3.csv('data/movies.csv', function(error, data){
        if(error != null){
            alert("Could load data/movies.csv!");
        }
        else{
            for(var i=0;i<data.length;i++){
                var temp = data[i];
                //temp.movieID = parseInt(temp.movieID);
                temp.year = parseInt(temp.year);
                movieID2Idx[temp.movieID] = i;
                movieInfo.push(temp);
            }
            movieViewSelectMenu.movieInit();
        }
    });

    d3.csv('data/users.csv', function(error, data){
        if(error != null){
            alert("Could load data/users/csv!");
        }
        else{
            for(var i=0;i<data.length;i++){
                var temp = data[i];
                //temp.userID = parseInt(temp.userID);
                temp.age = parseInt(temp.age);
                //temp.occupation = parseInt(temp.occupation);
                userID2Idx [temp.userID] = i;
                userInfo.push(temp);
            }
            userViewSelectMenu.userInit();
        }
    })
}()