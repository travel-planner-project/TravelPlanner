import { Suspense, lazy } from 'react'
import PlanDetail from '../pages/PlanDetail'

const Home = lazy(() => import('../pages/Home'))
const Planner = lazy(() => import('../pages/Planner'))
const Profile = lazy(() => import('../pages/Profile'))
const SignIn = lazy(() => import('../pages/SignIn'))

const Feed = lazy(() => import('../pages/Home'))
const Board = lazy(() => import('../pages/Home'))

type RouterElement = {
  id: number
  path: string
  label: string
  element: React.ReactNode
  onNavBar: boolean
  withAuth: boolean
}

export const routerData: RouterElement[] = [
  {
    id: 0,
    path: '/profile',
    label: 'user name',
    element: (
      <Suspense fallback={<div>loading</div>}>
        <Profile />
      </Suspense>
    ),
    onNavBar: false,
    withAuth: true,
  },
  {
    id: 0,
    path: '/user/login',
    label: '로그인/회원가입',
    element: (
      <Suspense fallback={<div>loading</div>}>
        <SignIn />
      </Suspense>
    ),
    onNavBar: false,
    withAuth: false,
  },
  {
    id: 1,
    path: '/',
    label: 'HOME',
    element: (
      <Suspense fallback={<div>loading</div>}>
        <Home />
      </Suspense>
    ),
    onNavBar: true,
    withAuth: false,
  },
  {
    id: 2,
    path: '/planner',
    label: 'PLANNER',
    element: (
      <Suspense fallback={<div>loading</div>}>
        <Planner />
      </Suspense>
    ),
    onNavBar: true,
    withAuth: true,
  },
  {
    id: 3,
    path: '/feed',
    label: 'FEED',
    element: (
      <Suspense fallback={<div>loading</div>}>
        <Feed />
      </Suspense>
    ),
    onNavBar: false,
    withAuth: true,
  },
  {
    id: 4,
    path: '/board',
    label: 'BOARD',
    element: (
      <Suspense fallback={<div>loading</div>}>
        <Board />
      </Suspense>
    ),
    onNavBar: false,
    withAuth: false,
  },
  {
    id: 4,
    path: '/plandetail',
    label: 'PLANDETAIL',
    element: (
      <Suspense fallback={<div>loading</div>}>
        <PlanDetail />
      </Suspense>
    ),
    onNavBar: false,
    withAuth: false,
  },
]

export const NavBarContent: RouterElement[] = routerData.filter(router => router.onNavBar)
