module 	HC_SR04_TOP(
	input  				clk				, 
	input   			rstn			, 
	input   			echo			, // 距离信号
	input   wire		uart_rx			, // 串口输入 
	input 	wire		spi_miso		, // spi_miso
	input   wire [3:0]	key				,
	output   			trig			, // 触发测距信号
	output  wire [7:0]	sel				,
	output  wire [7:0]	seg				,
	output  wire [4:0]	led				, 
	output  wire 		uart_tx			,
	output 	wire		spi_cs			,
	output 	wire		spi_sck			,
	output 	wire		spi_mosi		,
	output 	wire		w5500_rst		,
	output 	wire 		beep
);

    localparam   CLK         =   26'd50000000   ;    // 时钟频率
    localparam   BAUD        =   17'd115200     ;     // 波特率

	wire	[18:00]		data_o		;
	wire 				clk_us		;
	
	wire				flag_1s		;
	wire 				tx_done		;
	wire 	[7 : 0]		data_rx		;
	wire 	[3 : 0]		key_flag	;
	wire	[3 :00]		key_out		;

	// 滤波程序待测试
	wire 				flag_out	;	// 开始输出标志，滤波数据满足条件
	wire 	[18: 0]		data_out	;

	sr04_top u_sr04_top(
		.clk         (clk		) ,
		.rstn        (rstn		) ,
		.echo        (echo		) , // 距离信号
		.trig        (trig		) , // 触发测距信号 
		.sel         (sel		) ,
		.seg         (seg		) ,
		.data_o		 (data_o	)  
	);

	UART_driver u_UART_driver(
    	.clk         (clk		),
    	.rstn        (rstn		),
		.data_in	 (data_o	),
		.key_flag	 ({key_out[0]&&key_flag[0],key_out[1]&&key_flag[1],key_out[2]&&key_flag[2],key_out[3]&&key_flag[3]}	),
    	.UART_rx     (uart_rx	),
    	.UART_tx     (uart_tx	)    
	);

	led_driver u_led_driver(
		.clk         (clk		),
		.rstn        (rstn		),
		.data_in	 (data_o	), //待显示数据
		.led         (led		)
	);

	net_driver u_net_driver(
    	.clk			(clk		),
		.rstn			(rstn		),
		.spi_miso		(spi_miso	),
        .data_in		(data_o		),
		.o_spi_cs		(spi_cs		),
		.o_spi_sck		(spi_sck	),
		.o_spi_mosi		(spi_mosi	),
		.o_w5500_rst	(w5500_rst	)
	);

	key_debounce u_key_debounce(
    	.clk     		(clk		),
    	.rstn    		(rstn		), 
    	.key     		(key[0]		), 
    	.flag 			(key_flag[0]),
		.key_value		(key_out[0]	)
	);
	
	key_debounce u_key_debounce1(
    	.clk     		(clk		),
    	.rstn    		(rstn		), 
    	.key     		(key[1]		), 
    	.flag 			(key_flag[1]),
		.key_value		(key_out[1]	)
	);

	key_debounce u_key_debounce2(
    	.clk     		(clk		),
    	.rstn    		(rstn		), 
    	.key     		(key[2]		), 
    	.flag 			(key_flag[2]),
		.key_value		(key_out[2]	)
	);

	key_debounce u_key_debounce3(
    	.clk     		(clk		),
    	.rstn    		(rstn		), 
    	.key     		(key[3]		), 
    	.flag 			(key_flag[3]),
		.key_value		(key_out[3]	)
	);	

	beep_driver u_beep_driver(
    .sys_clk			(clk		),
    .sys_rst_n			(rstn		),
    .dis				(data_o		),
	.key_flag	 ({key_out[0]&&key_flag[0],key_out[1]&&key_flag[1],key_out[2]&&key_flag[2],key_out[3]&&key_flag[3]}	),
	.beep				(beep		)
 );

endmodule