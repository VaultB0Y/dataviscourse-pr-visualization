
function updateGenre(error, data) {
    if (error !== null) {
        alert("Couldn't load the dataset!");
    } else {

        data.forEach(function (d) {
            d.avgRating = parseFloat(d.avgRating);
            d.recordNum = parseInt(d.recordNum);
        });
    }
    // Set up the scales
    var height = 150
        ,width = 300
        ,leftOff = 50
        ,barWidth = width/data.length;

    var ratingScale = d3.scale.linear()
        .domain([2.5, d3.max(data, function (d) {
            return d.avgRating;
        })])
        .range([0, height]);
    var numScale = d3.scale.linear()
        .domain([0, d3.max(data, function (d) {
            return d.recordNum;
        })])
        .range([0, height]);
    var xScale = d3.scale.ordinal()
        .domain(d3.range(0, data.length))
        .rangeBands([0, width]);


    // Axis
    var ratingAxisScale = d3.scale.linear()
        .domain([2.5, d3.max(data, function (d) {
            return d.avgRating;
        })])
        .range([height, 0]);
    var numAxisScale = d3.scale.linear()
        .domain([0, d3.max(data, function (d) {
            return d.recordNum;
        })])
        .range([height, 0]);

    var xAxis = d3.svg.axis().scale(xScale);
    var ratingAxis = d3.svg.axis().scale(ratingAxisScale).orient("left");
    var numAxis = d3.svg.axis().scale(numAxisScale).orient("left");


    d3.select('#aBar').append("g")
        .attr("class", "xAxis axis")
        .attr("transform", "translate(" + leftOff +"," + 200 + ")")
        .call(xAxis)
        .selectAll("text")
        .data(data)
        .attr("y", 10)
        .attr("x", 10)
        .attr("transform", "rotate(45)")
        .style("text-anchor", "start")
        .text(function (d) {
            return d.genre;
        });
    d3.select('#aBar').append('g')
        .attr("transform", "translate(" + 50 +"," + 50 + ")")
        .attr("class", "ratingAxis axis")
        .call(ratingAxis);

    var aBar = d3.select('#aBar').select('g').selectAll('rect')
        .data(data);

    aBar.enter().append("rect")
        .attr("class","barChart")
        .attr("opacity",0);

    aBar.attr('x',function(d,i){return leftOff + i * barWidth;})
        .attr('width', barWidth)
        .transition()
        .duration(1000)
        .attr("opacity",1)
        .attr('height',function(d){return ratingScale(d.avgRating);})
        .attr('y',function(d){
            return  -ratingScale(d.avgRating);
        });


    aBar.exit()
        .transition()
        .duration(1000)
        .attr("opacity",0)
        .remove();
    aBar.on("mouseover",function(d){d3.select(this).style("fill","red");});
    aBar.on("mouseout",function(d){d3.select(this).style("fill","steelblue");})



    d3.select('#bBar').append("g")
        .attr("class", "xAxis axis")
        .attr("transform", "translate(" + leftOff +"," + 200 + ")")
        .call(xAxis)
        .selectAll("text")
        .data(data)
        .attr("y", 10)
        .attr("x", 10)
        .attr("transform", "rotate(45)")
        .style("text-anchor", "start")
        .text(function (d) {
            return d.genre;
        });

    d3.select('#bBar').append('g')
        .attr("transform", "translate(" + 50 +"," + 50 + ")")
        .attr("class", "numAxis axis")
        .call(numAxis);

    var bBar = d3.select('#bBar').select('g').selectAll('rect')
        .data(data);
    bBar.enter().append("rect")
        .attr("class","barChart")
        .attr("opacity",0);
    bBar.attr('x',function(d,i){return leftOff + i * barWidth})
        .attr('y', function(d){return -numScale(d.recordNum);})
        .attr('width', barWidth)
        .transition()
        .duration(1000)
        .attr("opacity",1)
        .attr('height',function(d){return numScale(d.recordNum);});
    bBar.exit()
        .transition()
        .duration(1000)
        .attr("opacity",0)
        .remove();
    bBar.on("mouseover",function(d){d3.select(this).style("fill","red");});
    bBar.on("mouseout",function(d){d3.select(this).style("fill","steelblue");});


    //
    //Pie Chart
    //
    height = 400;
    width = 400;
    radius = Math.min(height, width) / 2;

    var color = d3.scale.category20();
    var arc = d3.svg.arc()
        .outerRadius(radius - 10)
        .innerRadius(0);

    var pie = d3.layout.pie()
        .sort(null)
        .value(function(d) { return d.recordNum; });

    var svg = d3.select("#aPie").append("svg")
        .attr("width", width)
        .attr("height", height)
        .append("g")
        .attr("transform", "translate(" + width / 2 + "," + height / 2 + ")");

      data.forEach(function(d) {
        d.recordNum = + d.recordNum;
    });

    var g = svg.selectAll(".arc")
        .data(pie(data))
        .enter().append("g")
        .attr("class", "arc");

    g.append("path")
        .attr("d", arc)
        .style("fill", function(d, i) { return color(i); });

    g.append("text")
        .attr("transform", function(d) { return "translate(" + arc.centroid(d) + ")"; })
        .attr("dy", ".35em")
        .style("font", "8px sans-serif")
        .style("text-anchor", "middle")
        .text(function(d) {
            console.log(d);
            return d.data.genre;
        });


    //
    //TreeMap
    //
    // instantiate d3plus
    var treeMap = d3plus.viz()
        .container("#treeMap")  // container DIV to hold the visualization
        .data(data)  // data to use with the visualization
        .type("tree_map")   // visualization type
        .id("genre")         // key for which our data is unique on
        .size("recordNum")      // sizing of blocks
        .draw()             // finally, draw the visualization!

    //var aLineGenerator = d3.svg.line()
    //    .x(function (d, i) {
    //        return iScale(i);
    //    })
    //    .y(function (d) {
    //        return aScale(d.a);
    //    });
    //var aLine = d3.select('#aLine').select('path')
    //    .data(data)
    //    .transition()
    //    .duration(1000)
    //    .attr("opacity",1)
    //    .attr("d", aLineGenerator(data));
    //
    //
    //var bLineGenerator = d3.svg.line()
    //    .x(function (d, i) {
    //        return iScale(i);
    //    })
    //    .y(function (d) {
    //        return aScale(d.b);
    //    });
    //var bLine = d3.select('#bLine').select('path')
    //    .data(data)
    //    .transition()
    //    .duration(1000)
    //    .attr("opacity",1)
    //    .attr("d", bLineGenerator(data));
    //
    //var aAreaGenerator = d3.svg.area()
    //    .x(function (d, i) {
    //        return iScale(i);
    //    })
    //    .y0(0)
    //    .y1(function (d) {
    //        return aScale(d.a);
    //    });
    //var aArea = d3.select('#aArea').select('path')
    //    .data(data)
    //    .transition()
    //    .duration(1000)
    //    .attr("opacity",1)
    //    .attr("d", aAreaGenerator(data));
    //
    //
    //var bAreaGenerator = d3.svg.area()
    //    .x(function (d, i) {
    //        return iScale(i);
    //    })
    //    .y0(0)
    //    .y1(function (d) {
    //        return aScale(d.b);
    //    });
    //var bArea = d3.select('#bArea').select('path')
    //    .data(data)
    //    .transition()
    //    .duration(1000)
    //    .attr("opacity",1)
    //    .attr("d", bAreaGenerator(data));
    //
    //var scatter = d3.select('#scatter').selectAll('circle')
    //    .data(data);
    //scatter.enter().append("circle")
    //    .attr("class","circle")
    //    .attr("opacity",0);
    //scatter
    //    .transition()
    //    .duration(1000)
    //    .attr("opacity",1)
    //    .attr('cx',function(d,i){return aScale(d.a);})
    //    .attr('cy',function(d,i){return bScale(d.b);})
    //    .attr('r',6);
    //scatter.on("click", function(d,i){console.log(d.a);console.log(d.b)});
    //scatter.on("mouseover", function(d,i){
    //    d3.select("#scatter").append("text")
    //        .attr("id", "tooltip")
    //        .attr("x", 40)
    //        .attr("y", 20)
    //        .attr("text-anchor", "middle")
    //        .attr("font-family", "sans-serif")
    //        .attr("font-size", "11px")
    //        .attr("font-weight", "bold")
    //        .attr("fill", "black")
    //        .text(function(){
    //            return "X="+ d.a+",Y="+ d.b;
    //        });
    //});
    //scatter.on("mouseout",function(d,i){
    //    d3.select("#tooltip").remove();
    //});
    //scatter.exit()
    //    .transition()
    //    .duration(1000)
    //    .attr("opacity",0)
    //    .remove();
}



function updateYear(error, data) {
    if (error !== null) {
        alert("Couldn't load the dataset!");
    } else {
        data.forEach(function (d) {
            d.year = parseInt(d.year);
            d.avgRating = parseFloat(d.avgRating);
            d.recordNum = parseInt(d.recordNum);
        });
    }
    // Set up the scales
    var height = 150
        ,width = 700
        ,leftOff = 50
        ,barWidth = width/data.length;

    var ratingScale = d3.scale.linear()
        .domain([2.5, d3.max(data, function (d) {
            return d.avgRating;
        })])
        .range([0, height]);
    var numScale = d3.scale.linear()
        .domain([0, d3.max(data, function (d) {
            return d.recordNum;
        })])
        .range([0, height]);
    var xScale = d3.scale.linear()
        .domain([d3.min(data, function(d){return d.year;}),
        d3.max(data, function(d){return d.year;})])
        .range([0, width]);

    // Axis
    var ratingAxisScale = d3.scale.linear()
        .domain([2.5, d3.max(data, function (d) {
            return d.avgRating;
        })])
        .range([height, 0]);
    var numAxisScale = d3.scale.linear()
        .domain([0, d3.max(data, function (d) {
            return d.recordNum;
        })])
        .range([height, 0]);

    var xAxis = d3.svg.axis().scale(xScale);
    var ratingAxis = d3.svg.axis().scale(ratingAxisScale).orient("left");
    var numAxis = d3.svg.axis().scale(numAxisScale).orient("left");

    d3.select('#aLine').append("g")
        .attr("class", "xAxis axis")
        .attr("transform", "translate(" + leftOff +"," + 200 + ")")
        .call(xAxis)
        .selectAll("text")
        .text(function (d) {
            return d.toString();
        });
    d3.select('#aLine').append('g')
        .attr("transform", "translate(" + 50 +"," + 50 + ")")
        .attr("class", "ratingAxis axis")
        .call(ratingAxis);

    var ratingLineGen = d3.svg.line()
        .x(function (d) {
            return xScale(d.year) + leftOff;
        })
        .y(function (d) {
            return -ratingScale(d.avgRating);
        });

    var ratingLine = d3.select('#aLine').select('path')
        .data(data)
        .transition()
        .duration(1000)
        .attr("opacity",1)
        .attr()
        .attr("d", ratingLineGen(data));


    d3.select('#bLine').append("g")
        .attr("class", "xAxis axis")
        .attr("transform", "translate(" + leftOff +"," + 200 + ")")
        .call(xAxis)
        .selectAll("text")
        .text(function (d) {
            return d.toString();
        });

    d3.select('#bLine').append('g')
        .attr("transform", "translate(" + 50 +"," + 50 + ")")
        .attr("class", "numAxis axis")
        .call(numAxis);

    var numLineGen = d3.svg.line()
        .x(function (d) {
            return xScale(d.year) + leftOff;
        })
        .y(function (d) {
            return -numScale(d.recordNum);
        });

    var numLine = d3.select('#bLine').select('path')
        .data(data)
        .transition()
        .duration(1000)
        .attr("opacity",1)
        .attr("d", numLineGen(data));
}

function updateRating(error, data) {
    if (error !== null) {
        alert("Couldn't load the dataset!");
    } else {
        data.forEach(function (d) {
            d.userID = parseInt(d.userID);
            d.movieID = parseFloat(d.movieID);
            d.rating = parseFloat(d.rating);
        });
    }

    //
    // Box Plot
    //
    console.log(data);
    var boxPlot = d3plus.viz()
        .container('#boxPlot')
        .data(data)
        .type("box")
        .id("userID")
        .x("movieID")
        .y("rating")
        //.ui([{
        //    "label": "Visualization Type",
        //    "method": "type",
        //    "value": ["scatter","box"]
        //}])
        .draw();
}


function loadData() {
    d3.csv('data/Genre.csv', updateGenre);
    d3.csv('data/Year.csv', updateYear);
    d3.csv('data/UserRating.csv', updateRating);
}
