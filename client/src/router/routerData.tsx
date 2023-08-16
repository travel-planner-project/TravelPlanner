import { Suspense, lazy } from 'react'

// const Home = lazy(() => import('../pages/Home'))
const Planner = lazy(() => import('../pages/Planner'))
const Profile = lazy(() => import('../pages/Profile/Profile'))
const SignIn = lazy(() => import('../pages/SignIn'))
const SignUp = lazy(() => import('../pages/SignUp'))
const PlanDetail = lazy(() => import('../pages/PlanDetail'))
const EditProfile = lazy(() => import('../pages/Profile/EditProfile'))
const EditPassword = lazy(() => import('../pages/Profile/EditPassword'))
const DeleteUser = lazy(() => import('../pages/Profile/DeleteUser'))
const Feed = lazy(() => import('../pages/Feed'))

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
    path: '/profile/:id',
    label: '프로필',
    element: (
      <Suspense fallback={<div>loading</div>}>
        <Profile />
      </Suspense>
    ),
    onNavBar: false,
    withAuth: true,
  },
  {
    id: 1,
    path: '/user/login',
    label: '로그인',
    element: (
      <Suspense fallback={<div>loading</div>}>
        <SignIn />
      </Suspense>
    ),
    onNavBar: false,
    withAuth: false,
  },
  {
    id: 2,
    path: '/user/register',
    label: '회원가입',
    element: (
      <Suspense fallback={<div>loading</div>}>
        <SignUp />
      </Suspense>
    ),
    onNavBar: false,
    withAuth: false,
  },
  {
    id: 3,
    path: '/',
    label: 'HOME',
    element: (
      <Suspense fallback={<div>loading</div>}>
        <Planner />
      </Suspense>
    ),
    onNavBar: false,
    withAuth: true,
  },
  {
    id: 4,
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
    id: 5,
    path: '/feed',
    label: 'FEED',
    element: (
      <Suspense fallback={<div>loading</div>}>
        <Feed />
      </Suspense>
    ),
    onNavBar: true,
    withAuth: false,
  },
  {
    id: 6,
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
    id: 7,
    path: '/plandetail/:planId',
    label: 'PLANDETAIL',
    element: (
      <Suspense fallback={<div>loading</div>}>
        <PlanDetail />
      </Suspense>
    ),
    onNavBar: false,
    withAuth: false,
  },
  {
    id: 8,
    path: '/editprofile',
    label: '프로필 수정',
    element: (
      <Suspense fallback={<div>loading</div>}>
        <EditProfile />
      </Suspense>
    ),
    onNavBar: false,
    withAuth: true,
  },
  {
    id: 9,
    path: '/editpassword',
    label: '비밀번호 변경',
    element: (
      <Suspense fallback={<div>loading</div>}>
        <EditPassword />
      </Suspense>
    ),
    onNavBar: false,
    withAuth: true,
  },
  {
    id: 10,
    path: '/user/delete',
    label: '회원탈퇴',
    element: (
      <Suspense fallback={<div>loading</div>}>
        <DeleteUser />
      </Suspense>
    ),
    onNavBar: false,
    withAuth: true,
  },
]

export const NavBarContent: RouterElement[] = routerData.filter(router => router.onNavBar)
