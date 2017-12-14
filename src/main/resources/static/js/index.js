var app = angular.module('app', ['ui.bootstrap']);
app.controller('MainController', function ($scope, $http) {
    $scope.data = {};
    $scope.rows = [];
    $scope.query = {};
    $scope.queryResult=[];

    $scope.queryreport = function () {
        //  alert("wo hai mei you zuo");
        $http({
            url: '/query',
            method: 'POST',
            data: $scope.query
        }).success(function (reports) {
            $scope.queryResult=[];
            for (var i in reports) {
                var row = reports[i];
                $scope.queryResult.push(row);
            }
            if(reports.length>0) {
                $scope.querytableshow.isShow = true;
            }
        });


    }

    $scope.edit = function (id) {

        for (var i in $scope.rows) {
            var row = $scope.rows[i];
            if (id == row.id) {

                $scope.data.id = row.id;
                $scope.data.mission = row.mission;
                $scope.data.leader = row.leader;
                $scope.data.goals = row.goals;
                $scope.data.execution = row.execution;
                $scope.data.highlight = row.highlight;
                $scope.data.problems = row.problems;
                $scope.data.remark = row.remark;
                $scope.data.missionFrom = row.missionFrom;
                var begindate=row.missionBegin.split("-");
                $scope.data.missionBegin = new Date(begindate[0],parseInt(begindate[1])-1,begindate[2]);
                var enddate=row.missionEnd.split("-");
                $scope.data.missionEnd = new Date(enddate[0],parseInt(enddate[1])-1,enddate[2]);


                $.notify({
                    title: '<strong>OH YEAH ：</strong>',
                    message: '开始编辑!'
                },{
                    type: 'success',
                    animate: {
                        enter: 'animated tada',
                        exit: 'animated tada'
                    }
                });

                return;
            }
        }
    };
    $scope.remove = function (id) {
        for (var i in $scope.rows) {
            var row = $scope.rows[i];
            if (id == row.id) {
                $scope.rows.splice(i, 1);
                return;
            }
        }
    };
    $scope.save = function () {
        $http({

            url: '/save',
            method: 'POST',
            data: $scope.data
        }).success(function (id) {
            //保存成功后更新数据 获得当周所有
            $scope.get(id);
            // console.log(id);
            $scope.thisweekshow.isShow=true;
            $scope.data={};
            $.notify({
                title: '<strong>OH YEAH ：</strong>',
                message: '保存成功!'
            },{
                type: 'success',
                animate: {
                    enter: 'animated tada',
                    exit: 'animated tada'
                }
            });
        });


    };


    $scope.del = function (id) {
        $http({
            url: '/delete?id=' + id,
            method: 'POST',
        }).success(function () {
            //删除成功后移除数据
            $scope.remove(id);
            $.notify({
                title: '<strong>OH YEAH ：</strong>',
                message: '删除成功!'
            },{
                type: 'success',
                animate: {
                    enter: 'animated tada',
                    exit: 'animated tada'
                }
            });
        });
    };

    $scope.get = function (id) {
        $http({
            url: '/get?id=' + id,
            method: 'GET',
        }).success(function (data) {
            for (var i in $scope.rows) {
                var row = $scope.rows[i];
                if (data.id == row.id) {
                    row.mission = data.mission;
                    row.leader = data.leader;
                    row.goals = data.goals;
                    row.missionFrom = data.missionFrom;
                    row.execution = data.execution;
                    row.highlight = data.highlight;
                    row.problems = data.problems;
                    row.remark = data.remark;
                    row.missionBegin = data.missionBegin;
                    row.missionEnd = data.missionEnd;
                    return;
                }
            }
            $scope.rows.push(data);
        });
    };
    $scope.getNames = function (key) {
        return $http.get("/leaders?key=" + key).then(function (response) {
            //console.log(response);
            return response.data;
        });
    };
    $scope.getMissions = function (key) {
        return $http.get("/mission?key=" + key).then(function (response) {
            // console.log(response);
            return response.data;
        });
    };

    $scope.thisweekshow={
        isShow:false
    };

    $scope.querytableshow={
        isShow:false
    };


    $scope.popupEnd = {
        opened: false
    };
    $scope.openEnd = function () {
        $scope.popupEnd.opened = true;
    };

    $scope.popupBegin = {
        opened: false
    };
    $scope.openBegin = function () {
        $scope.popupBegin.opened = true;
    };

    $http({
        url: '/findThisWeek',
        method: 'GET'
    }).success(function (rows) {
        for (var i in rows) {
            var row = rows[i];
            $scope.rows.push(row);
        }
        if(rows.length>0) {
            $scope.thisweekshow.isShow = true;
        }

    });

    $http({
        url: '/firstblood',
        method: 'GET'
    }).success(function (data) {
        if(data.leader===''){return;}
        var msg ='今日拿一血的是--> '+data.leader;
        $.notify({
            title: '<strong>号外：</strong>',
            message: msg
        },{
            type: 'danger',
            animate: {
                enter: 'animated hinge',
                exit: 'animated bounceOut'
            }
        });
    });


});