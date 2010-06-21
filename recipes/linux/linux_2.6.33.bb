require linux.inc

PR="r1"

# Mark archs/machines that this kernel supports
DEFAULT_PREFERENCE = "-1"
DEFAULT_PREFERENCE_ts72xx = "-1"
DEFAULT_PREFERENCE_mpc8641-hpcn = "1"
DEFAULT_PREFERENCE_p2020ds = "1"
DEFAULT_PREFERENCE_imote2 = "1"
DEFAULT_PREFERENCE_afeb9260 = "1"

SRC_URI = "${KERNELORG_MIRROR}/pub/linux/kernel/v2.6/${P}.tar.bz2;name=kernel \
           file://defconfig"

SRC_URI[kernel.md5sum] = "c3883760b18d50e8d78819c54d579b00"
SRC_URI[kernel.sha256sum] = "63e237de3b3c4c46a21833b9ce7e20574548d52dabbd1a8bf376041e4455d5c6"

SRC_URI_append_ts72xx = " \
                        file://0001-ts72xx_base.patch \
                        file://0002-ts72xx_force_machine-id.patch \
                        file://0003-ep93xx_cpuinfo.patch \
                        file://0004-ts72xx_sbcinfo.patch \
                        file://0005-ep93xx_eth.patch \
                        file://0006-ts72xx_ts_ser1.patch \
                        file://0007-ts72xx_rs485.patch \
                        file://0008-ts72xx_ts_eth100.patch \
                        file://0009-ts7200_cf_ide.patch \
                        file://0010-ts72xx_pata.patch \
                        file://0011-ep93xx_pm.patch \
                        file://0012-ts72xx_gpio_i2c.patch \
                        file://0013-ts72xx_dio_keypad.patch \
                        file://0014-ep93xx_spi.patch \
                        file://0015-ep93xx_cpufreq.patch \
                        file://0016-ts7200_nor_flash.patch \
                        "
SRC_URI_append_afeb9260 = " \
                        file://0001-RS-485-mode-of-USART1.patch \
                        file://0002-SRAM-for-ethernet-TX-patch.patch \
                        file://0003-SRAM-TX-buffers-implementation-from-atmel-to-fix-TX-.patch \
                        file://0004-Disallowing-non-power-of-2-ring-size-proper-resource.patch \
                        file://0005-Add-missing-header-file.patch \
                        file://0006-Enable-SPI1.patch \
                        file://0007-Adding-4th-serial-port.patch \
                        "



