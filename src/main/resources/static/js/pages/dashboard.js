/**
* Theme: Larkon - Responsive Bootstrap 5 Admin Dashboard
* Author: Techzaa
* Module/App: Dashboard
*/

//
// Conversions
// 
var options = {
    chart: {
        height: 292,
        type: 'radialBar',
    },
    plotOptions: {
        radialBar: {
            startAngle: -135,
            endAngle: 135,
            dataLabels: {
                name: {
                    fontSize: '14px',
                    color: "undefined",
                    offsetY: 100
                },
                value: {
                    offsetY: 55,
                    fontSize: '20px',
                    color: undefined,
                    formatter: function (val) {
                        return val + "%";
                    }
                }
            },
            track: {
                background: "rgba(170,184,197, 0.2)",
                margin: 0
            },
        }
    },
    fill: {
        gradient: {
            enabled: true,
            shade: 'dark',
            shadeIntensity: 0.2,
            inverseColors: false,
            opacityFrom: 1,
            opacityTo: 1,
            stops: [0, 50, 65, 91]
        },
    },
    stroke: {
        dashArray: 4
    },
    colors: ["#ff6c2f", "#22c55e"],
    series: [65.2],
    labels: ['Returning Customer'],
    responsive: [{
        breakpoint: 380,
        options: {
            chart: {
                height: 180
            }
        }
    }],
    grid: {
        padding: {
            top: 0,
            right: 0,
            bottom: 0,
            left: 0
        }
    }
}

var chart = new ApexCharts(
    document.querySelector("#conversions"),
    options
);

chart.render();


//
//Performance-chart
//
document.addEventListener("DOMContentLoaded", function () {
    var revenueElement = document.getElementById("revenueChart");
    if (!revenueElement) {
        console.error("Không tìm thấy phần tử #revenueChart!");
        return;
    }

    var revenueData = revenueElement.getAttribute("data-revenue");
    if (!revenueData) {
        console.error("Không có dữ liệu doanh thu từ Thymeleaf!");
        return;
    }

    try {
        revenueData = JSON.parse(revenueData);
    } catch (error) {
        console.error("Lỗi khi parse dữ liệu doanh thu:", error);
        return;
    }

    var chartOptions = {
        series: [{
            name: "Doanh thu",
            type: "bar",
            data: revenueData
        }],
        chart: {
            height: 350,
            type: "line",
            toolbar: { show: false },
            events: {
                dataPointSelection: function (event, chartContext, config) {
                    var selectedMonth = config.dataPointIndex + 1;
                    loadDailyRevenue(selectedMonth);
                    setActiveButton("1M");
                }
            }
        },
        stroke: { width: [0, 2], curve: "smooth" },
        fill: { type: "gradient", gradient: { opacityFrom: 0.5, opacityTo: 0 } },
        xaxis: { categories: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"] },
        yaxis: { min: 0 },
        colors: ["#ff6c2f"]
    };

    var chart = new ApexCharts(document.querySelector("#revenueChart"), chartOptions);
    chart.render();

    function loadDailyRevenue(month) {
        var dailyRevenue = Array.from({ length: 30 }, () => Math.floor(Math.random() * 5000));

        chart.updateOptions({
            series: [{ name: `Doanh thu Tháng ${month}`, type: "bar", data: dailyRevenue }],
            xaxis: { categories: Array.from({ length: 30 }, (_, i) => `Ngày ${i + 1}`) }
        });
    }

    document.getElementById("btn-1Y").addEventListener("click", function () {
        chart.updateOptions(chartOptions);
        setActiveButton("1Y");
    });

    function setActiveButton(active) {
        document.getElementById("btn-1M").classList.toggle("active", active === "1M");
        document.getElementById("btn-1Y").classList.toggle("active", active === "1Y");
    }
});


class VectorMap {


    initWorldMapMarker() {
        const map = new jsVectorMap({
            map: 'world',
            selector: '#world-map-markers',
            zoomOnScroll: true,
            zoomButtons: false,
            markersSelectable: true,
            markers: [
                { name: "Canada", coords: [56.1304, -106.3468] },
                { name: "Brazil", coords: [-14.2350, -51.9253] },
                { name: "Russia", coords: [61, 105] },
                { name: "China", coords: [35.8617, 104.1954] },
                { name: "United States", coords: [37.0902, -95.7129] }
            ],
            markerStyle: {
                initial: { fill: "#7f56da" },
                selected: { fill: "#22c55e" }
            },
            labels: {
                markers: {
                    render: marker => marker.name
                }
            },
            regionStyle: {
                initial: {
                    fill: 'rgba(169,183,197, 0.3)',
                    fillOpacity: 1,
                },
            },
        });
    }

    init() {
        this.initWorldMapMarker();
    }

}

document.addEventListener('DOMContentLoaded', function (e) {
    new VectorMap().init();
});