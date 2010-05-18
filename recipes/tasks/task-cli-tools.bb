DESCRIPTION = "A set of useful command line tools"
DESCRIPTION_${PN}-debug = "A set of command line tools useful for debugging"
SECTION = "console"
LICENSE = "MIT"
PV = "1.0"
PR = "r18"

inherit task

PACKAGES += "${PN}-debug"

RDEPENDS_${PN} = "\
  dbus-daemon-proxy \
  dosfstools \
  htop \
  iptables \
  lsof \
  mbuffer \
  mdbus2 \
  mtd-utils \
  mterm2 \
  nano \
  nfs-utils-client \
  nmon \
  powertop \
  screen \
  socat \
  sysstat \
"

RDEPENDS_${PN}-debug = "\
  evtest \
  devmem2 \
  i2c-tools \
  gdb \
  ltrace \
  mkdump \
  mioctl \
  procps \
  pxaregs \
  s3c24xx-gpio \
  s3c64xx-gpio \
  serial-forward \
  strace \
  tcpdump \
"
