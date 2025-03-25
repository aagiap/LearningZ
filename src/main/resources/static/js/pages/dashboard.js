
document.addEventListener("DOMContentLoaded", function () {
    fetch("/marketer/dashboard/registration-percentage")
        .then(response => response.json())
        .then(data => {
            let totalUsersEl = document.getElementById("totalUsers");
            let totalCoursesEl = document.getElementById("totalCourses");

            totalUsersEl.innerText = data.totalUsers.toLocaleString();
            totalCoursesEl.innerText = data.totalCourses.toLocaleString();

            let options = {
                chart: {
                    height: 320,
                    type: "radialBar"
                },
                series: [data.registrationPercentage],
                labels: ["Course Enrollment Rate"],
                colors: ["#ff6c2f"],
                plotOptions: {
                    radialBar: {
                        startAngle: -135,
                        endAngle: 135,
                        hollow: { size: "70%" },
                        dataLabels: {
                            name: { fontSize: "16px" },
                            value: {
                                fontSize: "24px",
                                formatter: function (val) {
                                    return val.toFixed(1) + "%";
                                }
                            }
                        }
                    }
                }
            };

            let chart = new ApexCharts(document.querySelector("#conversions"), options);
            chart.render();
        })
        .catch(error => console.error("Error while retrieving registration rate data", error));
});


document.addEventListener("DOMContentLoaded", function () {
    var revenueElement = document.getElementById("revenueChart");

    fetch("/api/revenue/monthly")
        .then(response => response.json())
        .then(data => {
            initRevenueChart(data.months, data.revenue);
        })
        .catch(error => console.error("Error when retrieving monthly revenue data:", error));

    function initRevenueChart(categories, revenueData) {
        var chartOptions = {
            series: [
                {
                    name: "Revenue",
                    type: "bar",
                    data: revenueData
                }
            ],
            chart: {
                height: 350,
                type: "line",
                toolbar: { show: false },
                zoom: { enabled: false },
                animations: {
                    enabled: true,
                    easing: "easeout",
                    speed: 800,
                },
                events: {
                    dataPointSelection: function (event, chartContext, config) {
                        var selectedMonth = config.dataPointIndex + 1;
                        loadDailyRevenue(selectedMonth);
                        setActiveButton("1M");
                    }
                }
            },
            stroke: {
                width: [0, 3],
                curve: "smooth",
                colors: ["#22c55e"]
            },
            fill: {
                opacity: [1, 0.8],
                type: ["solid", "gradient"],
                gradient: {
                    shade: "light",
                    type: "vertical",
                    opacityFrom: 0.6,
                    opacityTo: 0,
                    stops: [0, 100]
                }
            },
            markers: {
                size: [0, 5],
                strokeWidth: 2,
                hover: {
                    size: 8,
                },
            },
            plotOptions: {
                bar: {
                    columnWidth: "35%",
                    borderRadius: 5,
                    barHeight: "80%",
                }
            },
            grid: {
                show: true,
                strokeDashArray: 3,
            },
            xaxis: {
                categories: categories,
                axisTicks: { show: false },
                axisBorder: { show: false },
            },
            yaxis: {
                min: 0,
                labels: {
                    formatter: function (value) {
                        return value.toLocaleString() + "đ";
                    }
                }
            },
            colors: ["#ff6c2f"],
            tooltip: {
                shared: true,
                y: {
                    formatter: function (value) {
                        return value.toLocaleString() + "đ";
                    }
                }
            },
            legend: {
                position: "top",
                horizontalAlign: "center",
            }
        };

        var chart = new ApexCharts(document.querySelector("#revenueChart"), chartOptions);
        chart.render();

        function loadDailyRevenue(month) {
            const monthNames = [
                "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
            ];
            const monthName = monthNames[month - 1];

            fetch(`/api/revenue/daily?month=${month}`)
                .then(response => response.json())
                .then(data => {
                    chart.updateOptions({
                        series: [{ name: `${monthName} Revenue`, type: "bar", data: data.revenue }],
                        xaxis: { categories: data.days }
                    });
                })
                .catch(error => console.error("Error when retrieving daily revenue data:", error));
        }

        document.getElementById("btn-1Y").addEventListener("click", function () {
            chart.updateOptions({
                series: [{ name: "Revenue", type: "bar", data: revenueData }],
                xaxis: { categories: categories }
            });
            setActiveButton("1Y");
        });

        function setActiveButton(active) {
            document.getElementById("btn-1Y").classList.toggle("active", active === "1Y");
            document.getElementById("btn-1M").classList.toggle("active", active === "1M");
        }
    }
});