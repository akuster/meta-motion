SUMMARY = "Linux cnc "
DESCRIPTION = "Linux cnc"
HOMEPAGE = "git://git.linuxcnc.org/git/linuxcnc.git"
SECTION = "utils"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://${S}/README;md5=492101b63f3e45fc4955dcd2c4e002ce"

PV = "0.1"
PR = "r01"

SRCREV = "87eabd73e2b0ffb964c1dcbd1402909097b041d0"
SRC_URI = "git://git.linuxcnc.org/git/linuxcnc.git"

inherit autotools binconfig pkgconfig

S = "${WORKDIR}/git"

#LDFLAGS += "-lpthread"

DEPENDS = "pth glib-2.0 tcl gtk+ bwidget"
#DEPENDS += "libusb1 "
#DEPENDS += "libmodbus"
# libboost-python-dev

#EXTRA_OEMAKE += " "
EXTRA_OECONF = " --enable-simulator --without-libmodbus --without-libusb-1.0"
EXTRA_OECONF += "--with-tclConfig=${STAGING_DIR}/${MACHINE}/${libdir}/tclConfig.sh"
EXTRA_OECONF += "--with-tkConfig=${STAGING_DIR}/${MACHINE}/${libdir}/tclConfig.sh"
#EXTRA_OECONF = " --enable-run-in-place"
do_configure_prepend() {
    cd ${S}/src
    ./autogen.sh       
}

do_configure(){
    cd ${S}/src
    ./configure ${EXTRA_OECONF}
}

PARALLEL_MAKE = ""
#make 
do_compile() {
    cd ${S}/src
    make clean
    make
    suso make setuid
}

do_install() {
    oe_runmake intall-menu
}

