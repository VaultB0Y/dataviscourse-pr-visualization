/**
 * Created by shijunwei on 15/11/13.
 */
function View(){
    this.col = [];
    this.row = [];
    this.cood = [];
    this.viewCood = [];
    this.viewSize = [];
    this.xScale = 1;
    this.yScale = 1;
    this.viewType = barChart;
    this.validViewType = [];
    this.initView();
}

View.prototype.initView = function(_viewType){
    View.prototype = _viewType;
}

view

View.prototype.addCol = function(_attr){
    this.row.append(_attr);
    this.updateValidType();
}

View.prototype.getCol = function(){

}

View.prototype.removeCol = function(){

}

View.prototype.setRow = function(_attr){

}

View.prototype.getRow = function(){

}

View.prototype.removeRow = function(){

}

View.prototype.updateValidType = function(){

}


