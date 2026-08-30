import { Outlet } from 'react-router-dom'
import Navbar from '../navigation/Navbar'
import Footer from '../navigation/Footer'
export default function PublicLayout() {
  return (
    <div className="min-h-dvh flex flex-col">
      <Navbar/>
      <main className="flex-1 pt-14 sm:pt-16"><Outlet/></main>
      <Footer/>
    </div>
  )
}
