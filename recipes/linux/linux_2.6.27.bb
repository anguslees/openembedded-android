require linux.inc

PR = "r14"

# Mark archs/machines that this kernel supports
DEFAULT_PREFERENCE = "-1"
DEFAULT_PREFERENCE_boc01 = "1"
DEFAULT_PREFERENCE_progear = "1"
DEFAULT_PREFERENCE_simpad = "-1"
DEFAULT_PREFERENCE_ts72xx = "1"

SRC_URI = "${KERNELORG_MIRROR}/pub/linux/kernel/v2.6/linux-${PV}.tar.bz2;name=kernel \
           ${KERNELORG_MIRROR}/pub/linux/kernel/v2.6/patch-${PV}.45.bz2;apply=yes;name=stablepatch \
           file://defconfig "

SRC_URI_append_boc01 = "\
	file://boc01.dts \
	file://001-090114-sqn11x0-usb-hack.patch \
	file://004-081205-usb.patch \
	file://005-090226-isl12024.patch \
	file://007-081217-lm73.patch \
	file://008-081208-spi.patch \
	file://011-090115-gpio.patch \
	file://012-090219-capsense.patch \
	file://013-090306-lcd.patch \
	"

SRC_URI_append_progear = "file://progear-bl.patch\
                          file://progear-ac2.patch"

SRC_URI_append_simpad = "\
           file://linux-2.6.27-SIMpad-GPIO-MMC-mod.patch \
           file://linux-2.6.27-SIMpad-battery-old-way-but-also-with-sysfs.patch \
           file://linux-2.6.27-SIMpad-cs3-simpad.patch \
           file://linux-2.6.27-SIMpad-mq200.patch \
           file://linux-2.6.27-SIMpad-pcmcia.patch \
           file://linux-2.6.27-SIMpad-serial-gpio_keys-and-cs3-ro.patch.v2;apply=yes \
           file://linux-2.6.27-SIMpad-ucb1x00-switches.patch \
           file://linux-2.6.27-SIMpad-ucb1x00-ts-supend-and-accuracy.patch \
           file://linux-2.6.24-SIMpad-hostap_cs-shared-irq.patch \
           file://linux-2.6.24-SIMpad-orinoco_cs-shared-irq.patch \
           file://linux-2.6.24-SIMpad-rtc-sa1100.patch \
           file://connectplus-remove-ide-HACK.patch \
           "

SRC_URI_append_ts72xx = "\
           file://0001-TS72xx-update-memory-map-comments.patch \
           file://0002-GPIO-fix.patch \
           file://0003-Debounce-IRQ.patch \
           file://0004-OHCI-fix.patch \
           file://0005-Fix-wrong-machine-ID-passed-from-RedBoot.patch \
           file://0006-Force-the-nF-bit-on.patch \
           file://0007-Use-CPLD-watchdog-to-reset.patch \
           file://0008-Fix-UART-clocks.patch \
           file://0009-CPU-info-and-board-revision.patch \
           file://0010-GPIO-leds.patch \
           file://0011-EP93xx-Ethernet-support.patch \
           file://0012-TS72xx-watchdog.patch \
           file://0013-TS7200-NOR-physmap-fix.patch \
           file://0014-TS-7200-8MB-NOR-flash.patch \
           file://0015-TS-72xx-MAX197-support.patch \
           file://0016-RS485-common-bits.patch \
           file://0017-TS-72xx-SBC-proc-info.patch \
           file://0018-EP93xx-GPIO-I2C.patch \
           file://0019-EP93xx-SPI-driver.patch \
           file://0020-TS-72XX-LCD-console-driver.patch \
           file://0021-EP93xx-GPIO-matrix-keypad.patch \
           file://0022-TS-72xx-RS485-auto-mode-support.patch \
           file://0023-Clean-and-invalidate-D-cache-entry.patch \
           file://0024-PC-104-I-O-and-memory-mappings.patch \
           file://0025-EP93xx-discontigmem.patch \
           file://0026-TS72xx-PATA-support.patch \
           file://0027-TS72xx-TS-SER1-support.patch \
           file://0028-TS72xx-TS-ETH100.patch \
           file://0029-EP93xx-Power-Management-Routines.patch \
           file://0030-EP93xx-CPUfreq-driver.patch \
           "

# see http://bugzilla.kernel.org/show_bug.cgi?id=11143
do_install_append() {
	if [ -f arch/${ARCH}/lib/crtsavres.o ]; then
		mkdir -p $kerneldir/arch/${ARCH}/lib
		cp -a arch/${ARCH}/lib/crtsavres.o $kerneldir/arch/${ARCH}/lib/
	fi
}


SRC_URI[kernel.md5sum] = "b3e78977aa79d3754cb7f8143d7ddabd"
SRC_URI[kernel.sha256sum] = "0e99bf9e83f4d1ae0c656741a84dfddfa9c4d2469cf35475f5939d02dc3e4393"
SRC_URI[stablepatch.md5sum] = "9ad766753f54ba0a4df7c42873e7aa16"
SRC_URI[stablepatch.sha256sum] = "fb78c95e902194ac62facdbad1a89430c991271a35e4bd30ce9dad2f948d99c8"
