library verilog;
use verilog.vl_types.all;
entity cycloneiv_hssi_cmu_rxclk_ctl is
    port(
        pld_rx_clk      : in     vl_logic;
        rcvd_clk_pma    : in     vl_logic;
        rcvd_clk0_pma   : in     vl_logic;
        tx_pma_clk      : in     vl_logic;
        refclk_pma      : in     vl_logic;
        fref            : in     vl_logic;
        clklow          : in     vl_logic;
        scan_mode       : in     vl_logic;
        gen2ngen1       : in     vl_logic;
        gen2ngen1_bundle: in     vl_logic;
        rx_div2_sync_centrl: in     vl_logic;
        rx_div2_sync_quad_up: in     vl_logic;
        rx_div2_sync_quad_down: in     vl_logic;
        rrcvd_clk_sel   : in     vl_logic_vector(1 downto 0);
        rclk_1_sel      : in     vl_logic_vector(1 downto 0);
        rclk_2_sel      : in     vl_logic_vector(1 downto 0);
        rrx_rd_clk_sel  : in     vl_logic;
        rxrst           : in     vl_logic;
        rindv_rx        : in     vl_logic;
        rdwidth_rx      : in     vl_logic;
        rfreerun_rx     : in     vl_logic;
        rauto_speed_ena : in     vl_logic;
        rfreq_sel       : in     vl_logic;
        rrxpcsclkpwdn   : in     vl_logic;
        rmaster_rx      : in     vl_logic;
        rmaster_up_rx   : in     vl_logic;
        rself_sw_en_rx  : in     vl_logic;
        fref_muxed      : out    vl_logic;
        clklow_muxed    : out    vl_logic;
        rcvd_clk        : out    vl_logic;
        clk_1_b         : out    vl_logic;
        clk_2_b         : out    vl_logic;
        rx_wr_clk       : out    vl_logic;
        rx_rd_clk       : out    vl_logic;
        rx_clk          : out    vl_logic;
        rcvd_clk_pma_b  : out    vl_logic;
        clk_2_b_raw     : out    vl_logic;
        rx_wr_clk_raw   : out    vl_logic;
        rx_rd_clk_raw   : out    vl_logic;
        rx_div2_sync_out: out    vl_logic
    );
end cycloneiv_hssi_cmu_rxclk_ctl;
