require linux.inc

PR = "r13"

# Mark archs/machines that this kernel supports
DEFAULT_PREFERENCE = "-1"

DEFAULT_PREFERENCE_at91sam9263ek = "28"
DEFAULT_PREFERENCE_ronetix-pm9263 = "29"
DEFAULT_PREFERENCE_stb225 = "28"
DEFAULT_PREFERENCE_collie = "1"
DEFAULT_PREFERENCE_gamecube = "1"
DEFAULT_PREFERENCE_wrap = "1"
DEFAULT_PREFERENCE_tx27 = "1"
DEFAULT_PREFERENCE_nokia900 = "1"
DEFAULT_PREFERENCE_mh355 = "2"

SRC_URI = "${KERNELORG_MIRROR}/pub/linux/kernel/v2.6/linux-2.6.28.tar.bz2;name=kernel \
           ${KERNELORG_MIRROR}/pub/linux/kernel/v2.6/patch-${PV}.10.bz2;apply=yes;name=stablepatch \
           file://defconfig"

SRC_URI_append_at91sam9263ek = " \
           file://linux-2.6.28-at91.patch.bz2 \
	   file://linux-2.6.28-exp.patch.bz2 "

SRC_URI_append_ronetix-pm9263 = " \
           file://linux-2.6.28-at91.patch.bz2 \
           file://linux-2.6.28-exp.patch.bz2 \
           http://download.ronetix.info/sk-eb926x/linux/kernel/2.6.28/003_linux-2.6.28-at91-ronetix-20112009.patch;name=ronetixpatch "

SRC_URI_append_mh355 = " \
           file://linux-2.6.28-at91.patch.bz2 \
           file://linux-2.6.28-exp.patch.bz2 \
           file://linux-2.6.28.10-at91-mh.patch "

SRC_URI_append_stb225 = " \
           file://uImage.patch \
           file://ebase-fix.patch \
           file://enable-uart.patch \
           file://ip3902.patch"

SRC_URI_append_collie = " \
	file://0001-collie-start-scoop-converton-to-new-api.patch \
	file://0002-add-locomo_spi-driver.patch \
	file://0003-enable-cpufreq-for-collie.patch \
	file://0004-fix-dma-for-SA1100.patch \
	file://0005-fix-collie-keyboard-bug.patch \
	file://0006-add-collie-flash-hack.patch \
	file://0007-hostap-workaround-for-buggy-sa1100-pcmcia-driver.patch \
	file://0008-fix-collie-suspend-hack.patch \
	file://0009-add-sa1100-usb-gadget-driver-hack.patch \
	file://0010-mmc_spi-add-suspend-and-resume-callbacks.patch \
	file://0011-move-drivers-mfd-.h-to-include-linux-mfd.patch \
	file://0012-move-ucb1200-ts-driver.patch \
	file://0013-add-collie-touchscreen-driver.patch \
	file://0014-collie-locomo-led-change-default-trigger.patch \
	file://0015-SA1100-make-gpio_to_irq-and-reverse-a-macro.patch \
	file://0016-add-gpiolib-support-to-ucb1x00.patch \
	file://0017-collie-convert-to-gpiolib-for-ucb1x00.patch \
	file://0018-collie-add-battery-driver.patch \
	file://0019-collie-support-pda_power-driver.patch \
	file://0020-remove-collie_pm.c.patch \
	file://0021-mmc-trivial-annotation-of-blocks.patch \
	file://0022-mmc_block-print-better-error-messages.patch \
	file://0023-mmc_block-ensure-all-sectors-that-do-not-have-error.patch " 

SRC_URI_append_tosa = " \
	file://commit-31c9b28;apply=yes \
	file://commit-ddfb33c;apply=yes \
	file://commit-f34ee79;apply=yes \
	"
SRC_URI_append_gamecube = " \
	file://patch-2.6.28-gc;apply=yes \
	"

SRC_URI_append_tx27 = " \
	file://linux-2.6.28-karo4.diff \
	"

SRC_URI_nokia900 = "${KERNELORG_MIRROR}/pub/linux/kernel/v2.6/linux-2.6.28.tar.bz2;name=kernel \
		    http://repository.maemo.org/pool/maemo5.0/free/k/kernel/kernel_2.6.28-20094803.3+0m5.diff.gz;name=nokiapatch \
		    file://defconfig"

S = "${WORKDIR}/linux-2.6.28/"

SRC_URI[kernel.md5sum] = "d351e44709c9810b85e29b877f50968a"
SRC_URI[kernel.sha256sum] = "ae0d97c55efe7fce01273c97f8152af0deff5541e3bbf5b9ad98689112b54380"
SRC_URI[stablepatch.md5sum] = "64e6b226f1dc469755d82d0d8b677feb"
SRC_URI[stablepatch.sha256sum] = "f4a2f97f59d272571a4977916392628642a8e4388f94417a723dc4bdb0e47dc2"
SRC_URI[ronetixpatch.md5sum] = "22af1c0a7bdc5d0f4e83f17f91b0c524"
SRC_URI[ronetixpatch.sha256sum] = "da47c6e2ab51180be3b50d3cd219dbebe877121e4068aa5846fc1cd018082931"
SRC_URI[nokiapatch.md5sum] = "fdd13af46cbaf8594b9fc3d82070aecc"
SRC_URI[nokiapatch.sha256sum] = "78ab82b0d6647d196fe3f6185a743da4b1846730668b078beb814c717fdd0bb5"
