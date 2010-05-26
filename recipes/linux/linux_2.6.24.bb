require linux.inc

# Mark archs/machines that this kernel supports
DEFAULT_PREFERENCE = "-1"
DEFAULT_PREFERENCE_gesbc-9302 = "1"
DEFAULT_PREFERENCE_mpc8313e-rdb = "1"
DEFAULT_PREFERENCE_simpad = "1"
DEFAULT_PREFERENCE_atngw100 = "1"
DEFAULT_PREFERENCE_at32stk1000 = "1"
DEFAULT_PREFERENCE_hipox = "1"
DEFAULT_PREFERENCE_cs-e9302 = "1"
DEFAULT_PREFERENCE_smartq5 = "1"

PR = "r34"

SRC_URI = "${KERNELORG_MIRROR}/pub/linux/kernel/v2.6/linux-2.6.24.tar.bz2;name=kernel \
           ${KERNELORG_MIRROR}/pub/linux/kernel/v2.6/patch-${PV}.7.bz2;apply=yes;name=stablepatch \
           file://squashfs-lzma-2.6.24.patch \
           file://ubifs-v2.6.24.patch \
           file://defconfig"

# Moved away temporarely until committed properly (work in progress).
#           file://powerpc-clockres.patch \
#           file://leds-cpu-activity.patch \
#           file://leds-cpu-activity-powerpc.patch \

SRC_URI_append_simpad = "\
           file://linux-2.6.24-SIMpad-GPIO-MMC-mod.patch \
           file://linux-2.6.24-SIMpad-battery-old-way-but-also-with-sysfs.patch \
           file://linux-2.6.24-SIMpad-cs3-simpad.patch \
           file://linux-2.6.24-SIMpad-mq200.patch \
           file://linux-2.6.24-SIMpad-pcmcia.patch \
           file://linux-2.6.24-SIMpad-serial-gpio_keys-and-cs3-ro.patch.v2;apply=yes \
           file://linux-2.6.24-SIMpad-ucb1x00-switches.patch \
           file://linux-2.6.24-SIMpad-ucb1x00-ts-supend-and-accuracy.patch \
           file://linux-2.6.24-SIMpad-hostap_cs-shared-irq.patch \
           file://linux-2.6.24-SIMpad-orinoco_cs-shared-irq.patch \
           file://linux-2.6.24-SIMpad-rtc-sa1100.patch \
           file://linux-2.6.24-SIMpad-ucb1x00-audio.patch \
	   file://connectplus-remove-ide-HACK.patch \
	   file://collie-kexec.patch \
           file://export_atags-r2.patch \
           "

SRC_URI_append_gesbc-9302 = " \
	file://0001-gesbc-nand.patch \
	file://0002-gesbc-eth-platform.patch \
	file://0005-ep93xx-reboot.patch \
	"

SRC_URI_append_mpc8313e-rdb = "\
	file://mpc831x-nand.patch \
	file://mpc8313e-rdb-leds.patch \
	file://mpc8313e-rdb-rtc.patch \
	file://mpc8313e-rdb-cardbus.patch \
	"

CMDLINE_gesbc-9302 = "console=ttyAM0 root=mtd5 rootfstype=jffs2 mtdparts=GESBC-NAND:64m(app),-(data)"

SRC_URI_append_cm-x270 = " \
	file://0001-cm-x270-match-type.patch \
	file://0002-ramdisk_load.patch \
	file://0003-mmcsd_large_cards-r0.patch \
	file://0004-cm-x270-nand-simplify-name.patch \
	file://0005-add-display-set-default-16bpp.patch \
	"

SRC_URI_avr32 = "http://avr32linux.org/twiki/pub/Main/LinuxPatches/linux-2.6.24.3.atmel.3.tar.bz2;name=atmelpatch \
                 file://defconfig"
S_avr32 = "${WORKDIR}/linux-2.6.24.3.atmel.3"

SRC_URI_append_ts72xx = "\
	file://ep93xx-gpio-interrupt-debounce.diff \
	file://ep93xx-i2c-bus.diff \
	file://ep93xx-i2c.diff \
	file://ep93xx-leds.diff \
	file://ep93xx-serial-uartbaud.diff \
	file://ep93xx-serial-clocks.diff \
	file://ep93xx-timer-accuracy.diff \
	file://ep93xx-maverick-uniqid.patch \
	file://ep93xx-eth-phylib-framework.patch \
	file://ts72xx-nfbit-fix.patch \
	file://ts72xx-machine-id-fix.patch \
	file://ts72xx-watchdog.patch \
	file://ts72xx-use-cpld-reset.patch \
	file://ts72xx-rs485.patch"

SRC_URI_append_hipox = " \
	file://hipox-mach-type.patch \
	file://hipox.patch \
	file://hipox-uart.patch \
	file://hipox-pci-config-delay.patch \
	file://hipox-pci-max-size.patch \
	file://hipox-nand.patch \
	file://hipox-ubifs.patch \
	file://hipox-kconfig.patch \
	file://hipox-sata-module.patch \
	file://hipox-OXE-INT2.patch \
	file://hipox-rtc.patch \
	file://hipox-nand-vs-pci.patch \
	file://hipox-nand-vs-nor.patch \
	file://ox810-gmac-without-leon.patch \
	"

EXTRA_OEMAKE_smartq5 = " OBJCOPY=${OBJCOPY}"
SRC_URI_smartq5 = " ${KERNELORG_MIRROR}/pub/linux/kernel/v2.6/linux-2.6.24.tar.bz2;name=kernel \
    http://ftp.kernel.org/pub/linux/kernel/v2.6/patch-2.6.24.7.bz2;apply=yes;name=stablepatch \
    file://smartq-gitupdate.diff \
    file://base/0001-Apply-samsung-kernel-patch.patch \
    file://base/0002-Apply-smartq-patch.patch \
    file://mer/0001-Mer-keymappings-change.patch \
    file://mer/0002-no-DM9000.patch \
    file://mer/0003-Mer-WPA-fix.patch \
    file://mer/0004-Mer-hardwire-USB-OTG-gadget-type.patch \
    file://mer/0005-backlight-parameter-and-fixes.patch \
    file://mer/0006-tv-encoder.patch \
    file://mer/0007-make-tv-encoder-scaler-compile.patch \
    file://mer/0008-build-TV-by-default.patch \
    file://mer/0009-Apply-cpufreq-patch-from-gqwang.patch \
    file://mer/0010-Better-compatibility-with-some-memory-chips.patch \
    file://mer/0011-Only-reserve-memory-for-TV-if-CONFIG_VIDEO_SAMSUNG_T.patch \
    file://mer/0012-Disable-TV-out-to-save-RAM.patch \
    file://defconfig \
"


CMDLINE_cm-x270 = "console=${CMX270_CONSOLE_SERIAL_PORT},38400 monitor=1 mem=64M mtdparts=physmap-flash.0:256k(boot)ro,0x180000(kernel),-(root);cm-x270-nand:64m(app),-(data) rdinit=/sbin/init root=mtd3 rootfstype=jffs2"

FILES_kernel-image_gesbc-9302 = ""

python do_compulab_image() {
	import os
	import os.path
	import struct

	machine = bb.data.getVar('MACHINE', d, 1)
	if machine == "cm-x270":
	    deploy_dir = bb.data.getVar('DEPLOY_DIR_IMAGE', d, 1)
	    kernel_file = os.path.join(deploy_dir, bb.data.expand('${KERNEL_IMAGE_BASE_NAME}', d) + '.bin')
	    img_file = os.path.join(deploy_dir, bb.data.expand('${KERNEL_IMAGE_BASE_NAME}', d) + '.cmx270')

	    fo = open(img_file, 'wb')

	    image_data = open(kernel_file, 'rb').read()

	    # first write size into first 4 bytes
	    size_s = struct.pack('i', len(image_data))

	    # truncate size if we are running on a 64-bit host
	    size_s = size_s[:4]

	    fo.write(size_s)
	    fo.write(image_data)
	    fo.close()

	    os.chdir(deploy_dir)
	    link_file = bb.data.expand('${KERNEL_IMAGE_SYMLINK_NAME}', d) + '.cmx270'
	    img_file = bb.data.expand('${KERNEL_IMAGE_BASE_NAME}', d) + '.cmx270'
	    try:
		os.unlink(link_file)
	    except:
		pass
	    os.symlink(img_file, link_file)
}


addtask compulab_image after do_deploy before do_build


SRC_URI[kernel.md5sum] = "3f23ad4b69d0a552042d1ed0f4399857"
SRC_URI[kernel.sha256sum] = "413c64fbbcf81244cb5571be4963644a1e81166a2b0f008a016528363b65c5d3"
SRC_URI[stablepatch.md5sum] = "0c1c5d6d8cd82e18d62406d2f34d1d38"
SRC_URI[stablepatch.sha256sum] = "b6bbb0dea427aa733c37d58a94b819b523c8649d7605f498348de159380c28a1"

SRC_URI[atmelpatch.md5sum] = "952715cc523f4a77e7c5f94f608594c0"
SRC_URI[atmelpatch.sha256sum] = "d26a1de101692958fbca1d1be40fe52bd605636baea616a3e8ed96e422a3648d"
